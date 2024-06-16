package com.bucketstore.task.core.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItem {
    private String productCode;

    private String productName;

    private int price;

    private String size;

    private int quantity;
}
