package com.orrot.store.integration;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.JsonPath;
import com.orrot.store.AbstractContainerBaseTest;
import com.orrot.store.cart.adapter.input.json.CartItemPatch;
import com.orrot.store.cart.adapter.input.json.CartItemView;
import com.orrot.store.integration.data.CartExamples;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("During Cart Items Adding, Removing or Replacing")
public class CartItemsIntegrationTest extends AbstractContainerBaseTest {


    private static final Long CART_ID_CLIENT_UPDATE = -5L;
    private static final Long PRODUCT_ID_TO_ADD = -1L;
    private static final Long PRODUCT_ID_TO_REMOVE = -2L;
    private static final Long PRODUCT_ID_SET_QUANTITY = -3L;

    private static final Long CART_ID_TO_COUNT_ITEMS = -3L;
    private static final Gson gson = new Gson();

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("When modifying Cart Items")
    @Sql(value = { "/sql/paymentmethod/default_list.sql", "/sql/onlineclient/default_list.sql", "/sql/product/default_list.sql",
            "/sql/cart/cart_with_one_item.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS) // Prerequisites
    @Sql(value = { "/sql/cart/clean.sql", "/sql/paymentmethod/clean.sql", "/sql/onlineclient/clean.sql",
            "/sql/product/clean.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS) // clean
    class WhenModifyingCartItems {

        @Test
        @DisplayName("Should return no content status and add the item to the cart")
        void shouldReturnNoContentAndAddTheItemToCart() throws Exception {
            // When / Then
            var cartItemToAdd = CartExamples.cartItemPatch(CartItemPatch.CartOperation.ADD, PRODUCT_ID_TO_ADD, 3);
            mockMvc.perform(patch("/v1/carts/{cartId}/items", CART_ID_CLIENT_UPDATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(gson.toJson(cartItemToAdd)))
                    .andExpect(status().isNoContent());

            // Assert
            var response = mockMvc.perform(get("/v1/carts/{id}", CART_ID_CLIENT_UPDATE)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            var contentAsString = JsonPath.read(response.getContentAsString(), "$.items").toString();
            var listType = new TypeToken<ArrayList<CartItemView>>(){}.getType();
            List<CartItemView> items = new Gson().fromJson(contentAsString, listType);

            assertThat(items)
                    .extracting(CartItemView::productId, CartItemView::quantity)
                    .as("Should contain the product '%d' with quantity 3", PRODUCT_ID_TO_ADD)
                    .contains(tuple(PRODUCT_ID_TO_ADD, 3));
        }

        @Test
        @DisplayName("Should return no content status and remove the item from the cart")
        void shouldReturnNoContentAndRemoveTheItemFromTheCart() throws Exception {

            // When / Then
            var cartItemToRemove = CartExamples.cartItemPatch(CartItemPatch.CartOperation.REMOVE, PRODUCT_ID_TO_REMOVE, 1);
            mockMvc.perform(patch("/v1/carts/{cartId}/items", CART_ID_CLIENT_UPDATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(gson.toJson(cartItemToRemove)))
                    .andExpect(status().isNoContent());

            // Assert
            var response = mockMvc.perform(get("/v1/carts/{id}", CART_ID_CLIENT_UPDATE)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            var contentAsString = JsonPath.read(response.getContentAsString(), "$.items").toString();
            var listType = new TypeToken<ArrayList<CartItemView>>(){}.getType();
            List<CartItemView> items = new Gson().fromJson(contentAsString, listType);

            assertThat(items)
                    .extracting(CartItemView::productId)
                    .as("Should NOT contain the product '%d'", PRODUCT_ID_TO_REMOVE)
                    .doesNotContain(PRODUCT_ID_TO_REMOVE);
        }

        @Test
        @DisplayName("Should return no content status and allow arbitrary quantity for the item in the cart")
        void shouldReturnNoContentAndAllowArbitraryQuantityInItem() throws Exception {

            // When / Then
            var cartItemToAdd = CartExamples.cartItemPatch(CartItemPatch.CartOperation.SET_FIXED_QUANTITY, PRODUCT_ID_SET_QUANTITY, 45);
            mockMvc.perform(patch("/v1/carts/{cartId}/items", CART_ID_CLIENT_UPDATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(gson.toJson(cartItemToAdd)))
                    .andExpect(status().isNoContent());

            // Assert
            var response = mockMvc.perform(get("/v1/carts/{id}", CART_ID_CLIENT_UPDATE)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            var contentAsString = JsonPath.read(response.getContentAsString(), "$.items").toString();
            var listType = new TypeToken<ArrayList<CartItemView>>(){}.getType();
            List<CartItemView> items = new Gson().fromJson(contentAsString, listType);

            assertThat(items)
                    .extracting(CartItemView::productId, CartItemView::quantity)
                    .as("Should contain the product '%d' with quantity 45", PRODUCT_ID_SET_QUANTITY)
                    .contains(tuple(PRODUCT_ID_SET_QUANTITY, 45));
        }
    }

    @Nested
    @DisplayName("When counting items in the cart")
    @Order(2)
    @Sql(value = { "/sql/paymentmethod/default_list.sql", "/sql/onlineclient/default_list.sql", "/sql/product/default_list.sql",
            "/sql/cart/default_list_with_items.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS) // Prerequisites
    @Sql(value = { "/sql/cart/clean.sql", "/sql/paymentmethod/clean.sql", "/sql/onlineclient/clean.sql",
            "/sql/product/clean.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS) // clean
    class WhenCountingItemsInCart {

        @Test
        @DisplayName("Should return the total count of items in the cart (by summing the quantity of each item)")
        void shouldReturnTotalCountOfItems() throws Exception {
            // When / Then
            mockMvc.perform(get("/v1/carts/{cartId}/items/count", CART_ID_TO_COUNT_ITEMS)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.totalCount").value(6));
        }
    }

}
