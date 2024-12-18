package com.orrot.store.cart.domain.exception;

import com.orrot.store.common.exception.GeneralShoppingCartException;

public class QuantityLessThanZeroException extends GeneralShoppingCartException {
    public QuantityLessThanZeroException(String message) {
        super(message);
    }
}
