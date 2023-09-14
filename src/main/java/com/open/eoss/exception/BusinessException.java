package com.open.eoss.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(Exception e) {
        super(e);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Exception e){
        super(message, e);
    }
}