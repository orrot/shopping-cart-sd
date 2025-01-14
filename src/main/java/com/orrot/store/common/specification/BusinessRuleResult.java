package com.orrot.store.common.specification;

import java.util.List;

/**
 * Class that represents the result of a business rule. Additional to the boolean, this class can contain any notification about the rule.
 * Notification could be any message, but mainly could be errors.
 *
 * @param isRuleSatisfied
 * @param notifications
 */
public record BusinessRuleResult(

    boolean isRuleSatisfied,
    List<String> notifications
) {

    public static final BusinessRuleResult SUCCESS = new BusinessRuleResult(true, List.of());

    public boolean isRuleNotSatisfied() {
        return !isRuleSatisfied;
    }

    public static BusinessRuleResult withError(String errorMessage) {
        return new BusinessRuleResult(false, List.of(errorMessage));
    }

}
