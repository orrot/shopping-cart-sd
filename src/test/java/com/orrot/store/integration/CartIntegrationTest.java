package com.orrot.store.integration;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import com.orrot.store.AbstractContainerBaseTest;
import com.orrot.store.cart.adapter.input.json.CartItemView;
import com.orrot.store.cart.adapter.input.json.CartView;
import com.orrot.store.common.rest.ResourcesURI;
import com.orrot.store.integration.data.CartExamples;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("During General Cart Information Creation and Updates")
public class CartIntegrationTest extends AbstractContainerBaseTest {

    private static final Long CART_ID_CLIENT_UPDATE = -1L;
    private static final Long CART_ID_PAYMENT_UPDATE = -2L;
    private static final Long CART_ID_WITH_ITEMS = -3L;
    private static final Gson gson = new Gson();

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("When creating an empty cart")
    @Sql(value = "/sql/paymentmethod/default_list.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
    @Sql(value = { "/sql/cart/clean.sql", "/sql/paymentmethod/clean.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
    class WhenCreatingEmptyCart {

        @Test
        @DisplayName("Should return created status and ID of the created empty cart")
        void shouldReturnCreatedStatusAndGeneratedIdShouldBeReturned() throws Exception {
            // When / Then
            var cartToCreate = CartExamples.dummyToWrite();
            var result = mockMvc.perform(post("/v1/carts")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(gson.toJson(cartToCreate)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").isNumber())
                    .andReturn();

            // Assert1
            Integer id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

            mockMvc.perform(get("/v1/carts/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("When updating a Cart")
    @Sql(value = { "/sql/paymentmethod/default_list.sql", "/sql/onlineclient/default_list.sql", "/sql/product/default_list.sql" },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS) // Prerequisites
    @Sql(value = "/sql/cart/default_list.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS) // Data
    @Sql(value = { "/sql/cart/clean.sql", "/sql/paymentmethod/clean.sql", "/sql/onlineclient/clean.sql", "/sql/product/clean.sql" },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS) // Clean
    class WhenUpdatingCart {

        @Test
        @DisplayName("Should allow to update the client owner of the cart")
        void shouldReturnNoContentStatusAndUpdateOnlyTheClientOwner() throws Exception {

            // Then
            var cartToUpdate = CartExamples.dummyToWrite(-2L, null);
            mockMvc.perform(patch("/v1/carts/{id}", CART_ID_PAYMENT_UPDATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(gson.toJson(cartToUpdate)))
                    .andExpect(status().isNoContent())
                    .andExpect(jsonPath("$").doesNotExist());

            // Assert
            mockMvc.perform(get("/v1/carts/{id}", CART_ID_PAYMENT_UPDATE)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.paymentMethod.code").exists())
                    .andExpect(jsonPath("$.onlineClientOwnerId").value(-2));
        }

        @Test
        @DisplayName("Should allow to update the payment method of the cart")
        void shouldReturnNoContentStatusAndUpdateOnlyThePayment() throws Exception {

            // Then
            var cartToUpdate = CartExamples.dummyToWrite(null, "CASH");
            mockMvc.perform(patch("/v1/carts/{id}", CART_ID_PAYMENT_UPDATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(gson.toJson(cartToUpdate)))
                    .andExpect(status().isNoContent())
                    .andExpect(jsonPath("$").doesNotExist());

            // Assert
            mockMvc.perform(get("/v1/carts/{id}", CART_ID_PAYMENT_UPDATE)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.paymentMethod.code").value("CASH"))
                    .andExpect(jsonPath("$.onlineClientOwnerId").isNumber());
        }
    }

    @Nested
    @DisplayName("When getting the cart summary by id")
    @Sql(value = { "/sql/paymentmethod/default_list.sql", "/sql/onlineclient/default_list.sql", "/sql/product/default_list.sql" },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS) // Prerequisites
    @Sql(value = "/sql/cart/default_list_with_items.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS) // Data
    @Sql(value = { "/sql/cart/clean.sql", "/sql/paymentmethod/clean.sql", "/sql/onlineclient/clean.sql", "/sql/product/clean.sql" },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS) // Clean
    class WhenGettingCartById {

        @Test
        @DisplayName("Should return the cart with all items")
        void shouldReturnCartWithAllItems() throws Exception {
            // When / Then
            var result = mockMvc.perform(get(ResourcesURI.CARTS_URI + "/" + CART_ID_WITH_ITEMS)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(CART_ID_WITH_ITEMS))
                    .andExpect(jsonPath("$.items").isArray())
                    .andExpect(jsonPath("$.items.length()").value(2))
                    .andReturn();

            // Assert
            var cartView = gson.fromJson(result.getResponse().getContentAsString(), CartView.class);

            assertThat(cartView)
                    .as("All the fields for the cart should be non-null")
                    .extracting(CartView::id, CartView::onlineClientOwnerId, CartView::paymentMethod)
                    .doesNotContainNull();

            assertThat(cartView.items())
                    .as("All the fields for all the items inside the cart should be non-null")
                    .flatExtracting(
                            CartItemView::productId, CartItemView::productName,
                            CartItemView::quantity, CartItemView::currentPrice)
                    .doesNotContainNull();
        }
    }
}

