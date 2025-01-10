package com.orrot.store.common.specification;

import java.util.List;

public record BusinessRuleResult(

    boolean areRulesSatisfied,
    List<String> notifications
) {

    public static final BusinessRuleResult EMPTY = new BusinessRuleResult(true, List.of());

    public BusinessRuleResult(boolean areRulesSatisfied) {
        this(areRulesSatisfied, List.of());
    }

    public BusinessRuleResult(boolean areRulesSatisfied, String errorMessage) {
        this(areRulesSatisfied, List.of(errorMessage));
    }

}
