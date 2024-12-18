package com.orrot.store.cart.port.usecase;

import com.orrot.store.cart.domain.model.PaymentMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListPaymentMethodsUseCase {

    Page<PaymentMethod> listPaymentMethods(Pageable pageable);
}
