package com.orrot.store.cart.port.usecase;

import com.orrot.store.cart.domain.model.PaymentMethod;
import com.orrot.store.cart.port.input.ListPaymentMethodsInputPort;
import com.orrot.store.cart.port.output.PaymentMethodOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListPaymentMethodsUseCase implements ListPaymentMethodsInputPort {

    private final PaymentMethodOutputPort paymentMethodOutputPort;

    @Override
    public Page<PaymentMethod> listPaymentMethods(Pageable pageable) {
        return paymentMethodOutputPort.findAll(pageable);
    }
}
