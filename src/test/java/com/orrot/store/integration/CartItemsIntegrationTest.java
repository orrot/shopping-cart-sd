package com.orrot.store.integration;

import com.orrot.store.common.rest.ResourcesURI;
import com.orrot.store.integration.data.CartExamples;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CartItemsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // add item

    // remove item

    // update item quantity

    // count items

//    @Nested
//    @DisplayName("When adding an item to the cart")
//    @Order(1)
//    class WhenAddingItemToCart {
//
//        @Test
//        @DisplayName("Should return no content status")
//        void shouldReturnNoContentStatus() throws Exception {
//            // When / Then
//            var cartItemToAdd = CartExamples.dummyCartItemPatch();
//            mockMvc.perform(patch(ResourcesURI.CART_ITEMS_URI)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(gson.toJson(cartItemToAdd)))
//                    .andExpect(status().isNoContent());
//        }
//    }
//
//    @Nested
//    @DisplayName("When counting items in the cart")
//    @Order(2)
//    class WhenCountingItemsInCart {
//
//        @Test
//        @DisplayName("Should return the total count of items in the cart")
//        void shouldReturnTotalCountOfItems() throws Exception {
//            // When / Then
//            var cartId = 1L;
//            mockMvc.perform(get(ResourcesURI.CART_ITEMS_URI + "/count")
//                            .param("cartId", String.valueOf(cartId))
//                            .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("$.totalCount").value(2));
//        }
//    }
}
