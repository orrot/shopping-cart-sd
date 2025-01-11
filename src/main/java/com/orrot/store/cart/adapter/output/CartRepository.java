package com.orrot.store.cart.adapter.output;

import com.orrot.store.cart.adapter.output.jpa.CartJpaRepository;
import com.orrot.store.cart.adapter.output.jpa.entity.CartJpaEntity;
import com.orrot.store.cart.adapter.output.jpa.mapper.CartDomainMapper;
import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.cart.port.output.CartOutputPort;
import com.orrot.store.common.exception.DomainSavingException;
import com.orrot.store.common.jpa.BaseDomainRepository;
import io.vavr.control.Either;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.function.Function;

@Repository
@Transactional
@Validated
public class CartRepository extends BaseDomainRepository<Cart, CartJpaEntity, Long> implements CartOutputPort {

    private final CartJpaRepository cartJpaRepository;
    // Declare the mapper if needed here

    public CartRepository(CartJpaRepository cartJpaRepository, CartDomainMapper cartDomainMapper) {
        super(cartJpaRepository, cartDomainMapper);
        this.cartJpaRepository = cartJpaRepository;
    }

    @Override
    public void update(Cart cartToUpdate) {
        Either.<DomainSavingException, Cart>right(cartToUpdate)
                .map(Cart::getId)
                .map(cartId -> update(cartId, cartToUpdate))
                .getOrElseThrow(Function.identity());
    }

    @Override
    public long countById(Long cartId) {
        // Consider use Specification pattern if more conditions are required
        return cartJpaRepository.count(
                Example.of(CartJpaEntity.builder()
                        .id(cartId)
                        .build()));
    }
}
