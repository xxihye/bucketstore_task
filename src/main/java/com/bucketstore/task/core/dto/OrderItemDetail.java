package com.bucketstore.task.core.dto;


import com.bucketstore.task.core.util.constants.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderItemDetail {
    private String productCode;

    private String productName;

    private int price;

    private String size;

    private int quantity;

    private OrderStatus orderStatus;

    private int deductedShippingCost;
}
