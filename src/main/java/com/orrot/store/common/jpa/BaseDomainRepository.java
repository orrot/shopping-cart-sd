package com.orrot.store.common.jpa;

import com.orrot.store.common.exception.DomainSavingException;
import io.vavr.control.Either;

import java.util.Optional;
import java.util.function.Function;

/**
 * Base class for domain repositories. It provides common methods to create, update and find domain entities that are pretty much the same for all the cases.
 * @param <D> Any Domain Class
 * @param <E> Any Hibernate Entity Class
 * @param <I> The ID of the Hibernate Entity Class
 */
// TODO Unit tests
public abstract class BaseDomainRepository<D, E, I> {

    protected BaseDomainMapper<D, E> domainMapper;
    protected BaseJpaRepository<E, I> domainJpaRepository;

    public BaseDomainRepository(BaseJpaRepository<E, I> domainJpaRepository, BaseDomainMapper<D, E> domainMapper) {
        this.domainJpaRepository = domainJpaRepository;
        this.domainMapper = domainMapper;
    }

    /**
     * Creates a new domain. Maps the Domain to Hibernate Entity, persists the state and returns the result as Domain.
     */
    public D create(D domainToCreate) {
        return Optional.ofNullable(domainToCreate)
                .map(domainMapper::mapToJpaEntity)
                .map(domainJpaRepository::save)
                .map(domainMapper::mapToDomain)
                .orElseThrow(() -> new DomainSavingException("The object to be created must not be null"));
    }

    /**
     * Takes the domain and maps it to the Hibernate Entity. Then, it updates the values OVER the entity. This is pretty much important for hibernate
     * because the entity must be managed by the session to be updated. Not doing this in that way usually results in a detached/transient or simply not all the state
     * updated.
     */
    public D update(I id, D domainToUpdate) {
        return Either.<DomainSavingException, I>right(id)
                .map(domainJpaRepository::findById)
                .filterOrElse(Optional::isPresent,
                        optional -> new DomainSavingException("The ID '%d' does not exist", id))
                .map(Optional::get)
                .map(existingEntity -> domainMapper
                        .mapToExistingEntity(domainToUpdate, existingEntity))
                .map(domainJpaRepository::save)
                .map(domainMapper::mapToDomain)
                .getOrElseThrow(Function.identity());
    }

    /**
     * Finds a domain by its ID. If the domain is not found, it returns an empty Optional.
     */
    public Optional<D> findById(I id) {
        return domainJpaRepository.findById(id)
                .map(domainMapper::mapToDomain);
    }

}
