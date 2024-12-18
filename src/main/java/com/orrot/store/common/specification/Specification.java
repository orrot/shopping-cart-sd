package com.orrot.store.common.specification;

public interface Specification<T> {

    boolean isSatisfiedBy(T t);
}