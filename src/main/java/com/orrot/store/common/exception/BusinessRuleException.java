package com.orrot.store.common.exception;

import com.orrot.store.common.specification.BrokenRule;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.Optional;

@Getter
public class BusinessRuleException extends GeneralShoppingCartException {

    private Collection<BrokenRule> brokenRules;

    public BusinessRuleException(String message) {
        super(message);
    }

    public BusinessRuleException(String message, Collection<BrokenRule> brokenRules) {
        super(message);
        this.brokenRules = brokenRules;
    }

    public BusinessRuleException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessRuleException(Throwable cause, String message, Object ... args) {
        super(String.format(message, args), cause);
    }

    public boolean hasBrokenRules() {
        return Optional.ofNullable(brokenRules)
                .map(CollectionUtils::isNotEmpty)
                .orElse(false);
    }
}
