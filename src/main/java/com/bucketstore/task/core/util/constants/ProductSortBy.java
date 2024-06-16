package com.bucketstore.task.core.util.constants;

public enum ProductSortBy {
    PRODUCT_CODE("productCode"),
    PRODUCT_NAME("productName"),
    PRICE("price"),
    REG_ID("regId"),
    REG_DATE("regDate"),
    UPD_ID("updId"),
    UPD_DATE("updDate");

    private String value;

    ProductSortBy(String value) {
        this.value = value;
    }

    // 문자열로부터 ProductSortBy 가져오는 메서드
    public static ProductSortBy fromValue(String value) {
        for (ProductSortBy sortBy : values()) {
            if (sortBy.value.equals(value)) {
                return sortBy;
            }
        }
        throw new IllegalArgumentException("Unknown product sortBy: " + value);
    }

    // 입력값이 유효한 ProductSortBy 인지 검증하는 메서드
    public static boolean isValid(String value) {
        try {
            fromValue(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
