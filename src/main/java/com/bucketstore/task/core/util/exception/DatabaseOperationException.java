package com.bucketstore.task.core.util.exception;

import org.springframework.http.HttpStatus;

public class DatabaseOperationException extends ApiException {
    public DatabaseOperationException(String s) {
        super(s, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
