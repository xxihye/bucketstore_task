package com.bucketstore.task.core.util.exception;

import org.springframework.http.HttpStatus;

public class InsufficientStockException extends ApiException {
    public InsufficientStockException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
