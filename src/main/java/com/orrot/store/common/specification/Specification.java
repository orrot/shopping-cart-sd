package com.orrot.store.common.specification;

public interface Specification<T> {

    BusinessRuleResult areSatisfiedBy(T t);
}