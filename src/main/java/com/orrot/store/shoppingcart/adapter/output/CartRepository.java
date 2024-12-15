package com.orrot.store.shoppingcart.adapter.output;

import com.orrot.store.shoppingcart.adapter.output.jpa.CartJpaRepository;
import com.orrot.store.shoppingcart.adapter.output.jpa.mapper.CartDomainMapper;
import com.orrot.store.shoppingcart.domain.exception.DomainSavingException;
import com.orrot.store.shoppingcart.domain.model.Cart;
import com.orrot.store.shoppingcart.port.output.CartOutputPort;
import io.vavr.control.Either;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@Repository
@Transactional
@RequiredArgsConstructor
@Validated
public class CartRepository implements CartOutputPort {

    private final CartJpaRepository cartJpaRepository;
    private final CartDomainMapper cartDomainMapper;

    @Override
    public Cart create(@NotNull @Valid Cart cartToCreate) {
        return Optional.of(cartToCreate)
                .filter(newCart -> newCart.getId() == null)
                .map(cartDomainMapper::mapToJpaEntity)
                .map(cartJpaRepository::save)
                .map(cartDomainMapper::mapToDomain)
                .orElseThrow(() -> new DomainSavingException("The cart ID must be null to create a new one"));
    }

    @Override
    public Cart update(@NotNull @Valid Cart cartToUpdate) {
        return Either.<DomainSavingException, Cart>right(cartToUpdate)
                .map(Cart::getId)
                .filterOrElse(Objects::nonNull,
                        cartId -> new DomainSavingException("The cart ID must not be null to update a cart") )
                .map(cartJpaRepository::findById)
                .filterOrElse(Optional::isPresent,
                        optional -> new DomainSavingException("The cart ID '%d' does not exist", cartToUpdate.getId()))
                .map(Optional::get)
                .map(existingCartEntity -> cartDomainMapper.mapToExistingEntity(cartToUpdate, existingCartEntity))
                .map(cartJpaRepository::save)
                .map(cartDomainMapper::mapToDomain)
                .getOrElseThrow(Function.identity());
    }

    @Override
    public Optional<Cart> findById(Long cartId) {
        return cartJpaRepository.findById(cartId)
                .map(cartDomainMapper::mapToDomain);
    }

    @Override
    public boolean existsById(Long cartId) {
        return cartJpaRepository.existsById(cartId);
    }
}
