package com.orrot.store.common.exception;

public class UnExistingRelationshipException extends GeneralShoppingCartException {

    public UnExistingRelationshipException(String message) {
        super(message);
    }

    public UnExistingRelationshipException(String message, Object ... args) {
        super(message, args);
    }
}
