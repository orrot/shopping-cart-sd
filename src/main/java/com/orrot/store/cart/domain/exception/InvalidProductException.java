package com.orrot.store.cart.domain.exception;

import com.orrot.store.common.exception.GeneralShoppingCartException;

public class InvalidProductException extends GeneralShoppingCartException {
    public InvalidProductException(String message) {
        super(message);
    }
}
