package com.bucketstore.task.core.entity.order;


import com.bucketstore.task.core.dto.OrderItem;
import com.bucketstore.task.core.entity.product.Product;
import com.bucketstore.task.core.entity.product.ProductOption;
import com.bucketstore.task.core.util.constants.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderProduct {

    private Long orderProductNo;
    private Long orderNo;
    private Long productOptionNo;
    private OrderStatus orderStatus;
    private int price;
    private int quantity;

    public static OrderProduct createOrderProduct(ProductOption productOption, Product product, OrderItem orderItem){
        return OrderProduct.builder()
                .productOptionNo(productOption.getProductOptionNo())
                .price(product.getPrice())
                .quantity(orderItem.getQuantity())
                .orderStatus(OrderStatus.ORDER_COMPLETED)
                .build();
    }

}
