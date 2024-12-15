package com.orrot.store.shoppingcart.domain.exception;

public class BusinessRuleException extends ShoppingCartException {

    public BusinessRuleException(String message) {
        super(message);
    }

    public BusinessRuleException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessRuleException(Throwable cause, String message, Object ... args) {
        super(String.format(message, args), cause);
    }
}
