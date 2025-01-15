package com.orrot.store.cart.port.input;

import com.orrot.store.cart.domain.model.PaymentMethod;
import com.orrot.store.cart.port.output.PaymentMethodOutputPort;
import com.orrot.store.cart.port.usecase.ListPaymentMethodsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListPaymentMethodsInputPort implements ListPaymentMethodsUseCase {

    private final PaymentMethodOutputPort paymentMethodOutputPort;

    @Override
    public Page<PaymentMethod> listPaymentMethods(Pageable pageable) {
        return paymentMethodOutputPort.findAll(pageable);
    }
}
