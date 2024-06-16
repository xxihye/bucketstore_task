package com.bucketstore.task.core.util.constants;

import lombok.Getter;

@Getter
public enum OrderStatus {

    PAYMENT_COMPLETED("101"),       // 결제완료
    ORDER_COMPLETED("102"),         // 주문완료
    ORDER_CANCELLED("103"),         // 주문취소
    DELIVERY_PREPARING("200"),      // 배송준비
    DELIVERY_IN_PROGRESS("201"),    // 배송중
    DELIVERY_COMPLETED("203"),      // 배송완료
    PURCHASE_CONFIRMED("400"),      // 구매확정
    EXCHANGE_REQUESTED("301"),      // 교환접수
    EXCHANGE_COMPLETE("302"),       // 교환완료
    RETURN_REQUESTED("311"),        // 반품접수
    REFUND_COMPLETED("312"),        // 환불완료
    RETURN_COMPLETED("313");        // 반품완료

    private String orderStatusCode;

    OrderStatus(String orderStatusCode) {
        this.orderStatusCode = orderStatusCode;
    }

    public static OrderStatus fromCode(String code) {
        for (OrderStatus status : values()) {
            if (status.orderStatusCode.equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No enum constant with code: " + code);
    }
}
