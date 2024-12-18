package com.orrot.store.common.jpa;

public interface BaseDomainMapper<D, E> {

    E mapToJpaEntity(D domain);

    D mapToDomain(E entity);

    E mapToExistingEntity(D domain, E entity);
}
