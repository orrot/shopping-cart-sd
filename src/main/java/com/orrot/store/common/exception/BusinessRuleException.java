package com.orrot.store.common.exception;

import lombok.Getter;

@Getter
public class BusinessRuleException extends GeneralShoppingCartException {

    public BusinessRuleException(String message) {
        super(message);
    }
}
