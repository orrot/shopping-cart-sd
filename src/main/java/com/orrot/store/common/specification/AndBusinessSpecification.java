package com.orrot.store.common.specification;

import org.apache.commons.collections4.ListUtils;

public final class AndBusinessSpecification<T> extends AbstractBusinessSpecification<T> {

    private final BusinessSpecification<T> spec1;
    private final BusinessSpecification<T> spec2;

    public AndBusinessSpecification(final BusinessSpecification<T> spec1, final BusinessSpecification<T> spec2) {
        this.spec1 = spec1;
        this.spec2 = spec2;
    }

    public BusinessRuleResult isSatisfiedBy(final T t) {

        var resultSpec1 = spec1.isSatisfiedBy(t);
        var resultSpec2 = spec2.isSatisfiedBy(t);
        return new BusinessRuleResult(
                resultSpec1.areRulesSatisfied() && resultSpec2.areRulesSatisfied(),
                ListUtils.union(resultSpec1.notifications(), resultSpec2.notifications()));
    }
}