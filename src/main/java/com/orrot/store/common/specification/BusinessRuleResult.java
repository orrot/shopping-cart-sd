package com.orrot.store.common.specification;

import java.util.List;

public record BusinessRuleResult(

    boolean areRulesSatisfied,
    List<String> notifications
) {

    public static final BusinessRuleResult SUCCESS = new BusinessRuleResult(true, List.of());

    public static BusinessRuleResult withError(String errorMessage) {
        return new BusinessRuleResult(false, List.of(errorMessage));
    }

}
