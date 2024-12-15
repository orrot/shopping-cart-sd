package com.orrot.store.common.exception;

public class UnExistingRelationshipException extends RuntimeException {

    public UnExistingRelationshipException(String message) {
        super(message);
    }
}
