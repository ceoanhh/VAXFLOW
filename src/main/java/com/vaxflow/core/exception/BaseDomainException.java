package com.vaxflow.core.exception;

public abstract class BaseDomainException extends RuntimeException {
    public BaseDomainException(String message) {
        super(message);
    }
}
