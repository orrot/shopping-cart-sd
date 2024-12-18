package com.orrot.store.common.jpa;

import java.util.Optional;

public interface BaseJpaRepository<E, I> {

        E save(E entity);

        Optional<E> findById(I id);
}
