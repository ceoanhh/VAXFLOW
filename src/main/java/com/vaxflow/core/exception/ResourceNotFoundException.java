package com.vaxflow.core.exception;

public class ResourceNotFoundException extends BaseDomainException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}