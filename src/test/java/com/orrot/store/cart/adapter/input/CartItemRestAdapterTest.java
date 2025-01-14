package com.orrot.store.cart.adapter.input;

import com.google.gson.Gson;
import com.orrot.store.cart.adapter.input.json.CartItemPatch;
import com.orrot.store.cart.port.usecase.AddOrUpdateCartItemsUseCase;
import com.orrot.store.cart.port.usecase.CountCartItemsUseCase;
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
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CartItemRestAdapter.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Import({ SecurityConfig.class })
class CartItemRestAdapterTest {

    private static final Gson gson = new Gson();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddOrUpdateCartItemsUseCase addOrUpdateCartItemsUseCase;

    @MockBean
    private CountCartItemsUseCase countCartItemsUseCase;

    @Nested
    @DisplayName("When adding an item to the cart")
    class WhenAddingItemToCart {

        @Test
        @DisplayName("Should return no content status and perform the Patch operation")
        void shouldReturnNoContentStatus() throws Exception {
            var cartItemPatch = MockerFactory.createDummy(CartItemPatch.class);
            mockMvc.perform(patch("/v1/carts/{cartId}/items", 1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(gson.toJson(cartItemPatch)))
                    .andExpect(status().isNoContent());

            then(addOrUpdateCartItemsUseCase)
                    .should()
                    .addOrUpdateCartItem(cartItemPatch.operation(), 1L, cartItemPatch.productId(), cartItemPatch.quantity());
        }
    }

    @Nested
    @DisplayName("When getting count of cart items")
    class WhenGettingItemsFromCart {

        @Test
        @DisplayName("Should return ok status and the calculated total count")
        void shouldReturnOkStatusAndListOfCartItems() throws Exception {

            given(countCartItemsUseCase.countCartItems(1L))
                    .willReturn(7L);

            mockMvc.perform(get("/v1/carts/{cartId}/items/count", 1L)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.totalCount").value(7));
        }

        @Test
        @DisplayName("Should return not found status when cart does not exists")
        void shouldReturnNotFoundStatusWhenCartDoesNotExist() throws Exception {

            given(countCartItemsUseCase.countCartItems(1L))
                    .willThrow(new UnExistingResourceException("Cart not found"));

            mockMvc.perform(get("/v1/carts/{cartId}/items/count", 1L)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }
}