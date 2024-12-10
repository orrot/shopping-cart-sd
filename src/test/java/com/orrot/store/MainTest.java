package com.orrot.store;

import com.orrot.store.shoppingcart.adapter.output.CartRepository;
import com.orrot.store.shoppingcart.domain.model.Cart;
import com.orrot.store.shoppingcart.domain.model.PaymentMethod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MainTest {

    @Autowired
    private CartRepository cartRepository;

    @Test
    void test() {
        var cart = Cart.builder()
                .paymentMethod(PaymentMethod.builder()
                        .code("CASH")
                        .build())
                .build();

        cart.addCartItem(1L, 2);
        cartRepository.create(cart);
    }

    @Test
    void testUpdate() {
        var cart = Cart.builder()
                .id(51L)
                .paymentMethod(PaymentMethod.builder()
                        .code("VISA_CC")
                        .build())
                .build();

        cart.addCartItem(3L, 10);
        cartRepository.update(cart);
    }

    @Test
    void testObject() {

        var cart = cartRepository.findById(51L);
        System.out.println(cart);
    }
}
