package com.orrot.store.common.specification;

import org.apache.commons.collections4.ListUtils;

public final class AndSpecification<T> extends AbstractSpecification<T> {

    private final Specification<T> spec1;
    private final Specification<T> spec2;

    public AndSpecification(final Specification<T> spec1, final Specification<T> spec2) {
        this.spec1 = spec1;
        this.spec2 = spec2;
    }

    public BusinessRuleResult areSatisfiedBy(final T t) {
        // TODO Improve this code
        return new BusinessRuleResult(
                spec1.areSatisfiedBy(t).areRulesSatisfied() && spec2.areSatisfiedBy(t).areRulesSatisfied(),
                ListUtils.union(spec1.areSatisfiedBy(t).notifications(), spec2.areSatisfiedBy(t).notifications()));
    }
}