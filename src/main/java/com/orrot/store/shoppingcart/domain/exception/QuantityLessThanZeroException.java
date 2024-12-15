package com.orrot.store.shoppingcart.domain.exception;

public class QuantityLessThanZeroException extends ShoppingCartException {
    public QuantityLessThanZeroException(String message) {
        super(message);
    }
}
