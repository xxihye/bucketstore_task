package com.bucketstore.task.core.dto;

import com.bucketstore.task.core.entity.product.Product;
import com.bucketstore.task.core.entity.product.ProductOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ProductResponse {

    private String productCode;
    private String productName;
    private int price;
    private String color;
    private List<String> sizes;

    @Getter
    @AllArgsConstructor
    public static class MultipleProduct {
        private List<ProductResponse> products;
    }

    public static ProductResponse from(Product product, List<ProductOption> options) {
        return ProductResponse.builder()
                        .productCode(product.getProductCode())
                        .productName(product.getProductName())
                        .price(product.getPrice())
                        .color(options.get(0).getColor())
                        .sizes(options.stream().map(ProductOption::getSize).collect(Collectors.toList()))
                        .build();
    }

}
