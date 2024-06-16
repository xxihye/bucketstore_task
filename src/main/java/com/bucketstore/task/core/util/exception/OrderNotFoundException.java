package com.bucketstore.task.core.util.exception;

import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends ApiException {
    public OrderNotFoundException(String s) {
        super(s, HttpStatus.NOT_FOUND);
    }
}
