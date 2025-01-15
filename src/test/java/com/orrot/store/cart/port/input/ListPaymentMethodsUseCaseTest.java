package com.orrot.store.cart.port.input;

import com.orrot.store.cart.domain.model.PaymentMethod;
import com.orrot.store.cart.port.output.PaymentMethodOutputPort;
import com.orrot.store.cart.port.usecase.ListPaymentMethodsUseCase;
import com.orrot.store.common.podam.MockerFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ListPaymentMethodsUseCaseTest {

    @InjectMocks
    private ListPaymentMethodsUseCase listPaymentMethodsUseCase;

    @Mock
    private PaymentMethodOutputPort paymentMethodOutputPort;

    @Nested
    @DisplayName("When listing payment methods")
    class WhenListingPaymentMethods {

        @Test
        @DisplayName("Should return a page of payment methods")
        void shouldReturnPageOfPaymentMethods() {
            // Given
            Pageable pageable = PageRequest.of(0, 10);
            PaymentMethod paymentMethod = MockerFactory.createDummy(PaymentMethod.class);
            Page<PaymentMethod> paymentMethodsPage = new PageImpl<>(List.of(paymentMethod));

            given(paymentMethodOutputPort.findAll(pageable))
                    .willReturn(paymentMethodsPage);

            // Then
            Page<PaymentMethod> page = listPaymentMethodsUseCase.listPaymentMethods(pageable);

            // Assert
            assertThat(page)
                    .isNotNull()
                    .containsExactly(paymentMethod);
        }
    }
}