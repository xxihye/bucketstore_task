package com.bucketstore.task.core.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequest {
    @NotNull
    private String userId;
    @NotNull
    private String shippingAddress;
    @NotNull
    private String postalCode;
    @NotNull
    private String recipient;
    @NotNull
    private List<OrderItem> items;
}
