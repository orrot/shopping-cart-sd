package com.orrot.store;

import com.orrot.store.shoppingcart.adapter.output.CartItemJpaRepository;
import com.orrot.store.shoppingcart.adapter.output.CartRepository;
import com.orrot.store.shoppingcart.domain.model.Cart;
import com.orrot.store.shoppingcart.domain.model.PaymentMethod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

@SpringBootTest
public class MainTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemJpaRepository cartItemJpaRepository;

    @Test
    void test() {
        var cart = Cart.builder()
                .paymentMethod(PaymentMethod.builder()
                        .code("CASH")
                        .build())
                .build();

        cart.addOrUpdateItem(1L, "", BigDecimal.TEN, 2);
        cartRepository.create(cart);
    }

    @Test
    void testUpdate() {
        var cart = Cart.builder()
                .id(65L)
                .paymentMethod(PaymentMethod.builder()
                        .code("VISA_CC")
                        .build())
                .build();

        cart.addOrUpdateItem(3L, BigDecimal.TEN, 10);
        cartRepository.update(cart);
    }

    @Test
    void testUpdateNullFail() {
        var cart = Cart.builder()
                .id(null)
                .paymentMethod(PaymentMethod.builder()
                        .code("VISA_CC")
                        .build())
                .build();

        cart.addOrUpdateItem(3L, BigDecimal.TEN, 10);
        cartRepository.update(cart);
    }

    @Test
    void unexistingFail() {
        var cart = Cart.builder()
                .id(-50L)
                .paymentMethod(PaymentMethod.builder()
                        .code("VISA_CC")
                        .build())
                .build();

        cart.addOrUpdateItem(3L, BigDecimal.TEN, 10);
        cartRepository.update(cart);
    }

    @Test
    void testObject() {

        var cart = cartRepository.findById(65L);
        System.out.println(cart);
    }

    @Test
    void testObjectasdsds() {

        var cart = cartItemJpaRepository.findAll(Pageable.unpaged());
        System.out.println(cart);
    }


}
