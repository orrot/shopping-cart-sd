package com.orrot.store.common.exception;

public class GeneralShoppingCartException extends RuntimeException {

    public GeneralShoppingCartException(String message) {
        super(message);
    }

    public GeneralShoppingCartException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeneralShoppingCartException(Throwable cause, String message, Object ... args) {
        super(String.format(message, args), cause);
    }

    public GeneralShoppingCartException(String message, Object ... args) {
        super(String.format(message, args));
    }
}
