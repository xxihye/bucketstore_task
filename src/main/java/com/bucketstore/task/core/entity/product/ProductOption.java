package com.bucketstore.task.core.entity.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOption {

    private Long productOptionNo;
    private String productCode;
    private String size;
    private String color;
    private int stock;
}