package com.orrot.store;

import com.orrot.store.shoppingcart.adapter.output.CartRepository;
import com.orrot.store.shoppingcart.domain.model.Cart;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@ActiveProfiles("test")
@Sql(value = "/sql/cartRemove.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class TestContainers2Test extends AbstractContainerBaseTest {

    @Autowired
    private CartRepository cartRepository;

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class NestedTest  {

        @Test
        @Order(1)
        // @Sql({"/sql/cartRemove.sql", "/sql/cart.sql"})
        @Sql("/sql/cart.sql")
        public void after() {
            System.out.println("After");
            Cart cart = cartRepository.findById(-15L)
                    .orElseThrow();
            System.out.println(cart);
        }

        @Test
        @Order(2)
        public void myTestWithMySQL() {
            System.out.println("------------------- TEST 2");
            Cart cart = cartRepository.findById(-15L)
                    .orElseThrow();
            System.out.println(cart);
        }
    }



}
