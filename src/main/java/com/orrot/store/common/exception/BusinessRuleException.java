package com.orrot.store.common.exception;

import com.orrot.store.common.specification.BrokenRule;
import lombok.Getter;

import java.util.Collection;

@Getter
public class BusinessRuleException extends GeneralShoppingCartException {

    private Collection<BrokenRule> brokenRules;

    public BusinessRuleException(String message) {
        super(message);
    }
}
