package com.orrot.store.common.exception;

public class DomainSavingException extends GeneralShoppingCartException {

    public DomainSavingException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public DomainSavingException(String message, Object... args) {
        super(message.formatted(args));
    }
}
