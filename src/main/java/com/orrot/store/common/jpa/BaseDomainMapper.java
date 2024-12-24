package com.orrot.store.common.jpa;

import org.mapstruct.MappingTarget;

public interface BaseDomainMapper<D, E> {

    E mapToJpaEntity(D domain);

    D mapToDomain(E entity);

    E mapToExistingEntity(D domain, @MappingTarget E entity);
}
