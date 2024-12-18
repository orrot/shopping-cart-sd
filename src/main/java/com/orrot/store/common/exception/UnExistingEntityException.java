package com.orrot.store.common.exception;

public class UnExistingEntityException extends GeneralShoppingCartException {

    public UnExistingEntityException(String message) {
        super(message);
    }

    public UnExistingEntityException(String message, Object... args) {
        super(message.formatted(args));
    }
}
