package com.bucketstore.task.core.util.constants;

public enum OrderSortBy {
    ORDER_NO("orderNo"),
    ORDER_DATE("orderDate"),
    USER_ID("userId"),
    RECIPIENT("recipient"),
    TOTAL_AMOUNT("totalAmount");

    private String value;

    OrderSortBy(String value) {
        this.value = value;
    }

    // 문자열로부터 ProductSortBy 가져오는 메서드
    public static OrderSortBy fromValue(String value) {
        for (OrderSortBy sortBy : values()) {
            if (sortBy.value.equalsIgnoreCase(value)) {
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
