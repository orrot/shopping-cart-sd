package com.orrot.store.common.specification;

import java.util.Collection;

/**
 * Class that could be re-used by any other one to implement a Specification that returns a result instead of a boolean
 * @param <T>
 */
public abstract class AbstractBusinessSpecification<T> implements BusinessSpecification<T> {

    public abstract BusinessRuleResult isSatisfiedBy(T t);

    public AbstractBusinessSpecification<T> and(AbstractBusinessSpecification<T> specification) {
        return new AndBusinessSpecification<>(this, specification);
    }

    /**
     * Check all rules and return the result by combining all of them into a single one.
     * @param object
     * @param specifications
     * @return
     * @param <T>
     */

    public static <T> BusinessRuleResult checkAllRules(T object, Collection<? extends AbstractBusinessSpecification<T>> specifications) {

        return specifications
                .stream()
                .map(rule -> (AbstractBusinessSpecification<T>) rule)
                .reduce(AbstractBusinessSpecification::and)
                .map(specification -> specification.isSatisfiedBy(object))
                .orElse(BusinessRuleResult.SUCCESS);
    }
}
