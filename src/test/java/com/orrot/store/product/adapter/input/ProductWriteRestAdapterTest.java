package com.orrot.store.product.adapter.input;


import com.google.gson.Gson;
import com.orrot.store.common.rest.ResourcesURI;
import com.orrot.store.config.web.SecurityConfig;
import com.orrot.store.product.adapter.input.json.ProductWrite;
import com.orrot.store.product.adapter.input.json.mapper.ProductJsonViewMapper;
import com.orrot.store.product.adapter.input.json.mapper.ProductJsonViewMapperImpl;
import com.orrot.store.product.domain.model.Product;
import com.orrot.store.product.port.usecase.CreateProductUseCase;
import com.orrot.store.product.port.usecase.UpdateProductUseCase;
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

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductWriteRestAdapter.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Import({ProductJsonViewMapperImpl.class, SecurityConfig.class})
class ProductWriteRestAdapterTest {

    private static final Gson gson = new Gson();

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private ProductJsonViewMapper mapper;

    @MockBean
    private CreateProductUseCase createProductUseCase;

    @MockBean
    private UpdateProductUseCase updateProductUseCase;

    @Nested
    @DisplayName("When creating a product")
    class WhenCreatingProduct {

        @Test
        @DisplayName("Should return created status and ID of the created product")
        void shouldReturnCreatedStatusAndGeneratedIdShouldBeReturned() throws Exception {
            var product = createProduct();
            given(createProductUseCase.createProduct(product))
                    .willReturn(product.withId(40L));

            mockMvc.perform(post(ResourcesURI.PRODUCTS_URI)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(gson.toJson(createProductJson())))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(40L));
        }

    }

    @Nested
    @DisplayName("When updating a product")
    class WhenUpdatingProduct {

        @Test
        @DisplayName("Should return no content status and body should be empty")
        void shouldReturnNoContentStatusAndBodyEmpty() throws Exception {

            willDoNothing()
                    .given(updateProductUseCase)
                    .updateProduct(any());

            mockMvc.perform(put(ResourcesURI.PRODUCTS_URI + "/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(gson.toJson(createProductJson())))
                    .andExpect(status().isNoContent())
                    .andExpect(jsonPath("$")
                            .doesNotExist());
        }

    }

    private static Product createProduct() {
        return Product.builder()
                .name("Product")
                .currentPrice(new BigDecimal(100))
                .description("My Desc")
                .build();
    }

    private static ProductWrite createProductJson() {
        return ProductWrite.builder()
                .name("Product")
                .currentPrice(new BigDecimal(100))
                .description("My Desc")
                .build();
    }
}
