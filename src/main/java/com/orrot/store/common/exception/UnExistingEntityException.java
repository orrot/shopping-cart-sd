package com.orrot.store.common.exception;

public class UnExistingEntityException extends RuntimeException {

    public UnExistingEntityException(String message) {
        super(message);
    }
}
