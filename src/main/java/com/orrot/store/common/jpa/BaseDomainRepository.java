package com.orrot.store.common.jpa;

import com.orrot.store.common.exception.DomainSavingException;
import com.orrot.store.common.exception.GeneralShoppingCartException;
import com.orrot.store.common.exception.UnExistingResourceException;
import io.vavr.control.Either;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * Base class for domain repositories. It provides common methods to create, update and find domain entities that are pretty much the same for all the cases.
 * @param <D> Any Domain Class
 * @param <E> Any Hibernate Entity Class
 * @param <ID> The ID of the Hibernate Entity Class
 */
public abstract class BaseDomainRepository<D extends IdentifiableById<ID>, E, ID> {

    protected BaseDomainMapper<D, E> domainMapper;
    protected BaseJpaRepository<E, ID> domainJpaRepository;

    public BaseDomainRepository(BaseJpaRepository<E, ID> domainJpaRepository, BaseDomainMapper<D, E> domainMapper) {
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
    public D update(D domainToUpdate) {
        return Either.<GeneralShoppingCartException, D>right(domainToUpdate)
                .map(IdentifiableById::getId)
                .filterOrElse(Objects::nonNull,
                        optional -> new DomainSavingException("The ID '%d' must not be null"))
                .map(domainJpaRepository::findById)
                .filterOrElse(Optional::isPresent,
                        optional -> new UnExistingResourceException("The ID '%d' does not exist", domainToUpdate.getId()))
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
    public Optional<D> findById(ID id) {
        return domainJpaRepository.findById(id)
                .map(domainMapper::mapToDomain);
    }

    /**
     * Finds a domain by its ID. If the domain is not found, it returns an empty Optional.
     */
    public boolean existsById(ID id) {
        return domainJpaRepository.existsById(id);
    }

    /**
     * Finds a domain by its ID. If the domain is not found, it returns an empty Optional.
     */
    public Page<D> findAll(Pageable pageable) {
        return domainJpaRepository.findAll(pageable)
                .map(domainMapper::mapToDomain);
    }

}
