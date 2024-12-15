package com.orrot.store.shoppingcart.domain.exception;

public class ShoppingCartException extends RuntimeException {

    public ShoppingCartException(String message) {
        super(message);
    }

    public ShoppingCartException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShoppingCartException(Throwable cause, String message, Object ... args) {
        super(String.format(message, args), cause);
    }
}
