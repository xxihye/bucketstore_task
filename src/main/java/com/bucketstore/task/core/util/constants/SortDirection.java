package com.bucketstore.task.core.util.constants;

public enum SortDirection {
    ASC("asc"), DESC("desc");

    private String value;

    SortDirection(String value) {
        this.value = value;
    }

    // 문자열로부터 SortDirection 가져오는 메서드
    public static SortDirection fromValue(String value) {
        for (SortDirection sortDirection : values()) {
            if (sortDirection.value.equalsIgnoreCase(value)) {
                return sortDirection;
            }
        }
        throw new IllegalArgumentException("Unknown sortDirection: " + value);
    }

    // 입력값이 유효한 SortDirection 인지 검증하는 메서드
    public static boolean isValid(String value) {
        try {
            fromValue(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
