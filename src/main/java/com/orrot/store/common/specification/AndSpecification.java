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

        var resultSpec1 = spec1.areSatisfiedBy(t);
        var resultSpec2 = spec2.areSatisfiedBy(t);
        return new BusinessRuleResult(
                resultSpec1.isBusinessFlowCorrect() && resultSpec2.isBusinessFlowCorrect(),
                ListUtils.union(resultSpec1.notifications(), resultSpec2.notifications()));
    }
}