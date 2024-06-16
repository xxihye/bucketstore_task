package com.bucketstore.task.core.service;


import com.bucketstore.task.core.dto.*;
import com.bucketstore.task.core.entity.order.Order;
import com.bucketstore.task.core.entity.order.OrderProduct;
import com.bucketstore.task.core.entity.product.Product;
import com.bucketstore.task.core.entity.product.ProductOption;
import com.bucketstore.task.core.mapper.OrderMapper;
import com.bucketstore.task.core.mapper.ProductMapper;
import com.bucketstore.task.core.util.constants.OrderStatus;
import com.bucketstore.task.core.util.exception.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private final ProductMapper productMapper;
    private final OrderMapper orderMapper;

    private static final int FREE_SHIPPING_MINIMUM = 30000; //무료배송 조건 금액
    private static final int SHIPPING_COST = 3000;          //배송비

    @Transactional
    public void createOrder(OrderRequest orderRequest) {
        int totalAmount = 0;
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (OrderItem orderItem : orderRequest.getItems()) {
            //상품 조회
            Product product = productMapper.findByProductCode(orderItem.getProductCode());

            //조회 결과가 없는 경우, 예외처리
            if (product == null) {
                throw new ProductNotFoundException("Invalid product code: " + orderItem.getProductCode());
            }

            //상품 옵션 조회
            ProductOption productOption = productMapper.findProductOptionByCodeAndSize(orderItem.getProductCode(), orderItem.getSize());

            //조회 결과가 없는 경우, 예외처리
            if (productOption == null) {
                throw new ProductOptionNotFoundException("Invalid size option for product: " + orderItem.getProductCode());
            }

            //조회 결과가 없는 경우, 예외처리
            if (productOption.getStock() < orderItem.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product: " + orderItem.getProductCode() + " and size: " + orderItem.getSize());
            }

            // 총 결제 금액 계산
            totalAmount += (product.getPrice() * orderItem.getQuantity());

            //orderProduct 엔티티 생성
            orderProducts.add(OrderProduct.createOrderProduct(productOption, product, orderItem));
        }

        // 배송비 계산
        int shippingCost = (totalAmount >= FREE_SHIPPING_MINIMUM) ? 0 : SHIPPING_COST;
        totalAmount += shippingCost;

        // order entity 생성
        Order order = Order.createOrder(orderRequest, shippingCost, totalAmount);

        try{
            // orders 테이블에 주문 데이터 삽입
            orderMapper.insertOrder(order);

            // 주문 상품 데이터 삽입 및 재고 차감
            for(OrderProduct op : orderProducts){
                op.setOrderNo(order.getOrderNo());

                orderMapper.insertOrderProduct(op);
                productMapper.updateStock(op.getProductOptionNo(), op.getQuantity());
            }

        }catch(Exception e){
            throw new DatabaseOperationException("Fail to create order.");
        }
    }

    @Transactional
    public void cancelOrderProduct(Long orderProductNo) {
        //주문 상품 조회
        OrderProduct orderProduct = orderMapper.findOrderProductByOrderProductNo(orderProductNo);

        //조회 결과가 없는 경우 예외처리
        if(orderProduct == null){
            throw new OrderProductNotFoundException("Invalid order product no : " + orderProductNo);
        }

        //주문 취소 불가능 상태시 예외처리
        if(!isCancelable(orderProduct)){
            throw new OrderNotCancelableException("Order cannot be cancelled in its current state.");
        }

        //주문 및 주문 상품 조회
        Order order = orderMapper.findOrderByOrderNo(orderProduct.getOrderNo());

        try{
            //주문 상태 변경
            orderMapper.updateOrderStatus(orderProductNo, OrderStatus.ORDER_CANCELLED);

            //주문 결제 금액 변경
            orderMapper.updateTotalAmount(orderProduct.getOrderNo(), calculateNewTotal(order, orderProduct));

        }catch (Exception e){
            throw new DatabaseOperationException("Fail to cancel order product.");
        }
    }

    //주문 취소 가능 여부 확인
    private boolean isCancelable(OrderProduct orderProduct) {
        return orderProduct.getOrderStatus() == OrderStatus.ORDER_COMPLETED
                || orderProduct.getOrderStatus() == OrderStatus.DELIVERY_PREPARING;
    }

    //주문 취소 후 결제금액 재계산 (배송비 차감)
    private int calculateNewTotal(Order order, OrderProduct orderProduct) {
        return order.getTotalAmount() - SHIPPING_COST
                                      - (orderProduct.getPrice() * orderProduct.getQuantity());
    }

    public Optional<List<OrderResponse>> getOrders(String sortBy, String sortDirection, int limit) {
        //주문 목록 조회
        List<Order> orders = orderMapper.findOrders(sortBy, sortDirection, limit);

        //조회결과 없을 경우, 예외처리
        if (orders.isEmpty()) {
            return Optional.empty();
        }

        //각 주문별 주문 상품 목록 조회 및 DTO 변환
        List<OrderResponse> orderResponses = orders.stream()
                                            .map(order -> OrderResponse.createOrderResponse(order,
                                                                                            orderMapper.findOrderItemsByOrderNo(order.getOrderNo())))
                                            .collect(Collectors.toList());

        return Optional.of(orderResponses);
    }

    public OrderDetailResponse getOrder(Long orderNo) {
        //주문 데이터 조회
        Order order = orderMapper.findOrderByOrderNo(orderNo);

        //조회결과 없을 경우, 예외처리
        if(order == null){
            throw new OrderNotFoundException("Invalid order no " + orderNo);
        }

        //주문 상품 내역 조회
        List<OrderItemDetail> orderItemDetails = orderMapper.findOrderItemDetailsByOrderNo(orderNo);

        //주문 취소인 상품인 경우, 배송비 차감 내역 설정
        orderItemDetails.stream().filter(orderItem -> orderItem.getOrderStatus() == OrderStatus.ORDER_CANCELLED)
                                        .forEach(o -> o.setDeductedShippingCost(SHIPPING_COST));

        //원 주문 금액 계산
        int originTotal = calculateOriginTotal(orderItemDetails);

        return OrderDetailResponse.from(order, orderItemDetails, originTotal);
    }

    //주문 상품 목록을 통한 원 주문 금액 계산
    public int calculateOriginTotal(List<OrderItemDetail> items){
        return items.stream().mapToInt(i -> i.getQuantity() * i.getPrice()).sum();
    }

}
