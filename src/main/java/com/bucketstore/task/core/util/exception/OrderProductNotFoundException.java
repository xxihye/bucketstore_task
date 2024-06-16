package com.bucketstore.task.core.util.exception;

import org.springframework.http.HttpStatus;

public class OrderProductNotFoundException extends ApiException {
    public OrderProductNotFoundException(String msg) {
        super(msg, HttpStatus.NOT_FOUND);
    }
}
