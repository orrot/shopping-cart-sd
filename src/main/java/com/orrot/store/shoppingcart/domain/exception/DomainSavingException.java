package com.orrot.store.shoppingcart.domain.exception;

public class DomainSavingException extends RuntimeException {

    public DomainSavingException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
    public DomainSavingException(String message, Object... args) {
        super(message.formatted(args));
    }
}
