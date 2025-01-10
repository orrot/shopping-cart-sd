package com.orrot.store.common.specification;

import java.util.Collection;

public abstract class AbstractSpecification<T> implements Specification<T> {

    public abstract BusinessRuleResult areSatisfiedBy(T t);

    public AbstractSpecification<T> and(AbstractSpecification<T> specification) {
        return new AndSpecification<>(this, specification);
    }

    public static <T> BusinessRuleResult checkAllRules(T object, Collection<? extends AbstractSpecification<T>> specifications) {

        return specifications
                .stream()
                .map(rule -> (AbstractSpecification<T>) rule)
                .reduce(AbstractSpecification::and)
                .map(specification -> specification.areSatisfiedBy(object))
                .orElse(BusinessRuleResult.EMPTY);
    }
}
