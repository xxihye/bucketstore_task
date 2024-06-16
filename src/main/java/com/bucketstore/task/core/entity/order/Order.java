package com.bucketstore.task.core.entity.order;

import com.bucketstore.task.core.dto.OrderRequest;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Order {

    private Long orderNo;
    private LocalDateTime orderDate;
    private String userId;
    private String shippingAddress;
    private String postalCode;
    private String recipient;
    private int shippingCost;
    private int totalAmount;

    public static Order createOrder(OrderRequest orderRequest, int shippingCost, int totalAmount){
        return Order.builder()
                    .userId(orderRequest.getUserId())
                    .shippingAddress(orderRequest.getShippingAddress())
                    .postalCode(orderRequest.getPostalCode())
                    .recipient(orderRequest.getRecipient())
                    .shippingCost(shippingCost)
                    .totalAmount(totalAmount).build();
    }

}
