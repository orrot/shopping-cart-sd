package com.orrot.store.common.specification;

import java.util.Collection;
import java.util.List;

public abstract class AbstractSpecification<T> implements Specification<T> {

    public abstract boolean isSatisfiedBy(T t);

    public String getUnsatisfiedReason() {
        return "Specification '%s' not satisfied".formatted(this.getClass());
    }

    public static <T> List<BrokenRule> checkAllSatisfied(T object, Collection<? extends AbstractSpecification<T>> specifications) {
        return specifications
                .stream()
                .filter(specification -> !specification.isSatisfiedBy(object))
                .map(AbstractSpecification::getUnsatisfiedReason)
                .map(BrokenRule::new)
                .toList();
    }
}
