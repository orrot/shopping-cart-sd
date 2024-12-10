package com.orrot.store.shoppingcart.domain.exception;

public class QuantityLessThanZeroException extends RuntimeException {
    public QuantityLessThanZeroException(String message) {
        super(message);
    }
}
