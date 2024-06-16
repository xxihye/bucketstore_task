package com.bucketstore.task.core.entity.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Product {

    private String productCode;
    private String productName;
    private int price;
    private String regId;
    private String updId;
    private LocalDateTime regDate;
    private LocalDateTime updDate;

}