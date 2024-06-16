package com.bucketstore.task.core.util.exception;

import org.springframework.http.HttpStatus;

public class OrderNotCancelableException extends ApiException {
    public OrderNotCancelableException(String s) {
        super(s, HttpStatus.METHOD_NOT_ALLOWED);
    }
}
