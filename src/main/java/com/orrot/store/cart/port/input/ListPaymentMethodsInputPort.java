package com.orrot.store.cart.port.input;

import com.orrot.store.cart.adapter.output.PaymentMethodRepository;
import com.orrot.store.cart.domain.model.PaymentMethod;
import com.orrot.store.cart.port.usecase.ListPaymentMethodsUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListPaymentMethodsInputPort implements ListPaymentMethodsUseCase {

    private final PaymentMethodRepository paymentMethodRepository;

    @Override
    public Page<PaymentMethod> listPaymentMethods(Pageable pageable) {
        return paymentMethodRepository.findAll(pageable);
    }
}
