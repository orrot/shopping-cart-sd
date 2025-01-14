package com.orrot.store.common.specification;

import org.apache.commons.collections4.ListUtils;

import java.util.Collection;

/**
 * Class that could be re-used by any other one to implement a Specification that returns a result instead of a boolean
 * @param <T>
 */
public interface BusinessSpecification<T> {

    BusinessRuleResult isSatisfiedBy(T t);

    /**
     * Combine two rules in one so object T is checked and notifications are joined.
     * @param spec1
     * @param spec2
     * @return
     * @param <T>
     */
    static <T> BusinessSpecification<T> and(final BusinessSpecification<T> spec1, final BusinessSpecification<T> spec2) {
        return object -> {
            var resultSpec1 = spec1.isSatisfiedBy(object);
            var resultSpec2 = spec2.isSatisfiedBy(object);
            return new BusinessRuleResult(
                    resultSpec1.isRuleSatisfied() && resultSpec2.isRuleSatisfied(),
                    ListUtils.union(resultSpec1.notifications(), resultSpec2.notifications()));
        };
    }

    /**
     * Check all rules and return the result by combining all of them into a single one.
     * @param object
     * @param specifications
     * @return
     * @param <T>
     */
    static <T> BusinessSpecification<T> and(T object, Collection<? extends BusinessSpecification<T>> specifications) {

        return specifications
                .stream()
                .map(rule -> (BusinessSpecification<T>) rule)
                .reduce(anyT -> BusinessRuleResult.SUCCESS, BusinessSpecification::and);
    }
}
