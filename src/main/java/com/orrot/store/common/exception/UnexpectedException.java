package com.orrot.store.common.exception;

public class UnexpectedException extends GeneralShoppingCartException {

    public UnexpectedException(Throwable cause) {
        super(cause, cause.getMessage());
    }
}
