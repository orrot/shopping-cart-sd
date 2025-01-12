package com.orrot.store.integration;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.JsonPath;
import com.orrot.store.AbstractContainerBaseTest;
import com.orrot.store.integration.data.ProductExamples;
import com.orrot.store.product.adapter.input.json.ProductView;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("During Product CRUD Flows")
public class ProductIntegrationTest extends AbstractContainerBaseTest {

    private static final Gson gson = new Gson();

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("When creating a product")
    @Order(1)
    class WhenCreatingProduct {

        @Test
        @DisplayName("Should return created status and ID of the created product")
        void shouldReturnCreatedStatusAndGeneratedIdShouldBeReturned() throws Exception {
            // When / Then
            var productToCreate = ProductExamples.dummyToWrite();
            var result = mockMvc.perform(post("/v1/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(gson.toJson(productToCreate)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").isNumber())
                    .andReturn();

            // Assert
            Integer id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

            mockMvc.perform(get("/v1/products/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(
                            gson.toJson(ProductExamples.dummy(id, productToCreate))));

        }
    }

    @Nested
    @DisplayName("When updating a product")
    @Order(2)
    class WhenUpdatingProduct {

        @Test
        @DisplayName("Should return no content status and body should be empty")
        void shouldReturnNoContentStatusAndBodyEmpty() throws Exception {

            // When
            var createdProduct = ProductExamples.dummyToWrite();
            var result = mockMvc.perform(post("/v1/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(gson.toJson(createdProduct)))
                    .andExpect(status().isCreated())
                    .andReturn();

            Integer productIdToUpdate = JsonPath.read(result.getResponse().getContentAsString(), "$.id");;

            // Then
            var productToUpdate = ProductExamples.update(createdProduct);
            mockMvc.perform(put("/v1/products/{id}", productIdToUpdate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(gson.toJson(productToUpdate)))
                    .andExpect(status().isNoContent())
                    .andExpect(jsonPath("$").doesNotExist());

            // Assert
            mockMvc.perform(get("/v1/products/{id}", productIdToUpdate)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(
                            gson.toJson(ProductExamples.dummy(productIdToUpdate, productToUpdate))));
        }

    }

    @Nested
    @DisplayName("When listing a product")
    class WhenListingProduct {

        @Test
        @DisplayName("Should return OK status and the page of products")
        @Sql(value = "/sql/product/default_list.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @Sql(value = "/sql/product/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        void shouldReturnOkAndThePageOfProducts() throws Exception {

            // When / Then
            var result = mockMvc.perform(get("/v1/products")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.totalElements").value(2))
                    .andReturn();

            // Assert
            var contentAsString = JsonPath.read(result.getResponse().getContentAsString(), "$.content").toString();
            var listType = new TypeToken<ArrayList<ProductView>>(){}.getType();
            List<ProductView> products = new Gson().fromJson(contentAsString, listType);

            assertThat(products)
                    .as("All the fields for all the products should be non-null")
                    .flatExtracting(ProductView::id, ProductView::name, ProductView::currentPrice, ProductView::description)
                    .doesNotContainNull();
        }

    }
}
