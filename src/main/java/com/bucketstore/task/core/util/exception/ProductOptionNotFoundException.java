package com.bucketstore.task.core.util.exception;

import org.springframework.http.HttpStatus;

public class ProductOptionNotFoundException extends ApiException {
    public ProductOptionNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
