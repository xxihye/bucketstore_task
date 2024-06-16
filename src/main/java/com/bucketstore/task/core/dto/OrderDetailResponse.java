package com.bucketstore.task.core.dto;


import com.bucketstore.task.core.entity.order.Order;
import com.bucketstore.task.core.util.TimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderDetailResponse {

    private Long orderNo;
    private String orderDate;
    private String userId;
    private String shippingAddress;
    private String postalCode;
    private String recipient;
    private int shippingCost;
    private int totalAmount;
    private int orgTotalAmount;
    private List<OrderItemDetail> items;

    public static OrderDetailResponse from(Order order, List<OrderItemDetail> items, int originTotal ){
        return OrderDetailResponse.builder()
                .orderNo(order.getOrderNo())
                .orderDate(TimeFormatter.format(order.getOrderDate()))
                .userId(order.getUserId())
                .shippingAddress(order.getShippingAddress())
                .postalCode(order.getPostalCode())
                .recipient(order.getRecipient())
                .shippingCost(order.getShippingCost())
                .totalAmount(order.getTotalAmount())
                .orgTotalAmount(originTotal)
                .items(items)
                .build();
    }

    @Getter
    @AllArgsConstructor
    public static class MultipleOrder {
        private List<OrderDetailResponse> orders;
    }
}
