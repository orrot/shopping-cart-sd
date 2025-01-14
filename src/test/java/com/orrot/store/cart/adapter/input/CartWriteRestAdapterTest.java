package com.orrot.store.cart.adapter.input;

import com.google.gson.Gson;
import com.orrot.store.cart.adapter.input.json.CartWrite;
import com.orrot.store.cart.adapter.input.json.mapper.CartJsonViewMapper;
import com.orrot.store.cart.adapter.input.json.mapper.CartJsonViewMapperImpl;
import com.orrot.store.cart.domain.model.Cart;
import com.orrot.store.cart.port.usecase.CreateEmptyCartUseCase;
import com.orrot.store.cart.port.usecase.UpdateCartInfoUseCase;
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

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CartWriteRestAdapter.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Import({CartJsonViewMapperImpl.class, SecurityConfig.class})
class CartWriteRestAdapterTest {

    private static final Gson gson = new Gson();

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private CartJsonViewMapper mapper;

    @MockBean
    private CreateEmptyCartUseCase createEmptyCartUseCase;
    
    @MockBean
    private UpdateCartInfoUseCase updateCartInfoUseCase;

    @Nested
    @DisplayName("When creating an empty cart")
    class WhenCreatingCart {

        @Test
        @DisplayName("Should return created status and return the ID of the Generated Cart")
        void shouldReturnCreatedStatusAndReturnIDCreateValidTheGeneratedCart() throws Exception {
            var cart = MockerFactory.createDummy(Cart.class);
            given(createEmptyCartUseCase.createEmptyCart(argThat(arg -> arg.getId() == null)))
                    .willReturn(cart.withId(20L));

            mockMvc.perform(post("/v1/carts")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(gson.toJson(MockerFactory.createDummy(CartWrite.class))))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(20L));
        }

    }

    @Nested
    @DisplayName("When updating a cart")
    class WhenUpdatingCart {

        @Test
        @DisplayName("Should return no content status and body should be empty")
        void shouldReturnNoContentStatusAndBodyEmpty() throws Exception {

            var cartWrite = MockerFactory.createDummy(CartWrite.class);

            mockMvc.perform(patch("/v1/carts/{id}", 1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(gson.toJson(cartWrite)))
                    .andExpect(status().isNoContent())
                    .andExpect(jsonPath("$").doesNotExist());

            then(updateCartInfoUseCase)
                    .should()
                    .updateCartInfo(1L, cartWrite.paymentMethodCode(), cartWrite.onlineClientOwnerId());
        }

    }
}