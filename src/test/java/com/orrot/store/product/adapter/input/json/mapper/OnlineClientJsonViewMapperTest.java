package com.orrot.store.product.adapter.input.json.mapper;

import com.orrot.store.product.adapter.input.json.ProductView;
import com.orrot.store.product.domain.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class OnlineClientJsonViewMapperTest {

    private final ProductJsonViewMapper mapper = Mappers.getMapper(ProductJsonViewMapper.class);

    @Nested
    @DisplayName("When Mapping Products to JSON Views")
    class WhenMappingJsonOnlineClientViewsToDomainProducts {

        @Test
        @DisplayName("Should map Product to JSON correctly")
        void shouldMapToDomain() {
            // Given
            var product = Product.builder()
                    .id(1L)
                    .name("Product Name")
                    .currentPrice(BigDecimal.valueOf(1_500))
                    .build();

            // When
            var productView = mapper.mapToView(product);

            // Then
            assertThat(productView)
                    .isNotNull()
                    .extracting(ProductView::id, ProductView::name, ProductView::currentPrice)
                    .containsExactly(1L, "Product Name", BigDecimal.valueOf(1_500));
        }
    }
}