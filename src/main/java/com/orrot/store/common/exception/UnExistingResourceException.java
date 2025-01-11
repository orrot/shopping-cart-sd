package com.orrot.store.common.exception;

public class UnExistingResourceException extends GeneralShoppingCartException {

    public UnExistingResourceException(String message) {
        super(message);
    }

    public UnExistingResourceException(String message, Object... args) {
        super(message.formatted(args));
    }
}
