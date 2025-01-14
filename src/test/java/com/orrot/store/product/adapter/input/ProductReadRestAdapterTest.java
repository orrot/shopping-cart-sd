package com.orrot.store.product.adapter.input;

import com.google.gson.Gson;
import com.orrot.store.common.podam.MockerFactory;
import com.orrot.store.config.web.SecurityConfig;
import com.orrot.store.product.adapter.input.json.mapper.ProductJsonViewMapper;
import com.orrot.store.product.adapter.input.json.mapper.ProductJsonViewMapperImpl;
import com.orrot.store.product.domain.model.Product;
import com.orrot.store.product.port.usecase.GetProductByIdUseCase;
import com.orrot.store.product.port.usecase.ListProductsUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductReadRestAdapter.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Import({ProductJsonViewMapperImpl.class, SecurityConfig.class})
class ProductReadRestAdapterTest {

    private static final Gson gson = new Gson();

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private ProductJsonViewMapper mapper;

    @MockBean
    private ListProductsUseCase listProductsUseCase;

    @MockBean
    private GetProductByIdUseCase getProductByIdUseCase;

    @Nested
    @DisplayName("When listing products")
    class WhenListingProducts {

        @Test
        @DisplayName("Should return ok status and a list of products")
        void shouldReturnOkStatusAndListCreateValidProducts() throws Exception {
            var product = MockerFactory.createDummy(Product.class);
            var pageable = PageRequest.of(0, 20);

            given(listProductsUseCase.listProducts(eq(pageable)))
                    .willReturn(new PageImpl<>(List.of(product), pageable, 1));

            mockMvc.perform(get("/v1/products")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.totalElements").value(1))
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content[0].id").value(product.getId()))
                    .andExpect(jsonPath("$.content[0].name").value(product.getName()))
                    .andReturn();
        }
    }

    @Nested
    @DisplayName("When getting a product by ID")
    class WhenGettingProductById {

        @Test
        @DisplayName("Should return ok status and the product")
        void shouldReturnOkStatusAndProduct() throws Exception {
            var product = MockerFactory.createDummy(Product.class);
            given(getProductByIdUseCase.findById(product.getId()))
                    .willReturn(product);

            mockMvc.perform(get("/v1/products/{id}", product.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(product.getId()))
                    .andReturn();
        }
    }
}