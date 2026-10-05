package com.vaxflow.invoice.service;

/** A user-safe message for an expected invoice or payment business rule violation. */
public class InvoiceBusinessException extends RuntimeException {

    public InvoiceBusinessException(String message) {
        super(message);
    }
}
