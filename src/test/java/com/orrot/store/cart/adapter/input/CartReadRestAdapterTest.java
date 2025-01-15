package com.orrot.store.cart.adapter.input;

import com.orrot.store.cart.adapter.input.json.mapper.CartJsonViewMapper;
import com.orrot.store.cart.adapter.input.json.mapper.CartJsonViewMapperImpl;
import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.cart.port.input.GetCartSummaryInputPort;
import com.orrot.store.common.exception.UnExistingResourceException;
import com.orrot.store.common.podam.MockerFactory;
import com.orrot.store.config.web.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CartReadRestAdapter.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Import({CartJsonViewMapperImpl.class, SecurityConfig.class})
class CartReadRestAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private CartJsonViewMapper mapper;

    @MockBean
    private GetCartSummaryInputPort getCartSummaryInputPort;

    @Nested
    @DisplayName("When getting a cart by ID")
    class WhenGettingCartById {

        @Test
        @DisplayName("Should return ok status and the cart")
        void shouldReturnOkStatusAndCart() throws Exception {
            var cart = MockerFactory.createDummy(Cart.class);
            given(getCartSummaryInputPort.findCartById(cart.getId()))
                    .willReturn(cart);

            mockMvc.perform(get("/v1/carts/{id}", cart.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(cart.getId()))
                    .andExpect(jsonPath("$.paymentMethod.code").value(cart.getPaymentMethodCode()))
                    .andExpect(jsonPath("$.paymentMethod.name").value(cart.getPaymentMethod().getName()))
                    .andExpect(jsonPath("$.onlineClientOwnerId").value(cart.getOnlineClientOwnerId()))
                    .andReturn();
        }

        @Test
        @DisplayName("Should return not found status when the cart does not exist")
        void shouldReturnNotFoundWhenCartDoesNotExist() throws Exception {

            given(getCartSummaryInputPort.findCartById(1L))
                    .willThrow(new UnExistingResourceException("Cart not found"));

            mockMvc.perform(get("/v1/carts/{id}", 1L)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }
}
