package com.orrot.store.common.specification;

public interface BusinessSpecification<T> {

    BusinessRuleResult isSatisfiedBy(T t);
}