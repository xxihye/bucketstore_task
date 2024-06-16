package com.bucketstore.task.core.dto;

import com.bucketstore.task.core.entity.order.Order;
import com.bucketstore.task.core.util.TimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class OrderResponse {
    private Long orderNo;
    private String orderDate;
    private String userId;
    private String shippingAddress;
    private String postalCode;
    private String recipient;
    private int shippingCost;
    private int totalAmount;
    private List<OrderItem> items;

    @Getter
    @AllArgsConstructor
    public static class MultipleOrder {
        private List<OrderResponse> orders;
    }

    public static OrderResponse createOrderResponse(Order order, List<OrderItem> items){
        return OrderResponse.builder()
                            .orderNo(order.getOrderNo())
                            .orderDate(TimeFormatter.format(order.getOrderDate()))
                            .userId(order.getUserId())
                            .shippingAddress(order.getShippingAddress())
                            .postalCode(order.getPostalCode())
                            .recipient(order.getRecipient())
                            .shippingCost(order.getShippingCost())
                            .totalAmount(order.getTotalAmount())
                            .items(items)
                            .build();
    }
}
