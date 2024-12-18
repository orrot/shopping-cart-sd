package com.orrot.store.cart.adapter.input.json.mapper;

import com.orrot.store.cart.adapter.input.json.PaymentMethodView;
import com.orrot.store.cart.domain.model.PaymentMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentMethodJsonViewMapperTest {

    private final PaymentMethodJsonViewMapper mapper =
            Mappers.getMapper(PaymentMethodJsonViewMapper.class);

    @Nested
    @DisplayName("When Mapping Payment Methods to JSON Views")
    class WhenMappingPaymentMethodsToJsonViews {

        @Test
        @DisplayName("Should map Payment Method to JSON correctly")
        void shouldMapToView() {
            // Given
            var paymentMethod = PaymentMethod.builder()
                    .code("CC")
                    .name("Credit Card")
                    .build();

            // When
            var paymentMethodView = mapper.mapToView(paymentMethod);

            // Then
            assertThat(paymentMethodView)
                    .isNotNull()
                    .extracting(PaymentMethodView::code, PaymentMethodView::name)
                    .containsExactly("CC", "Credit Card");
        }
    }
}