package com.orrot.store.cart.port.input;

import com.orrot.store.cart.domain.model.PaymentMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListPaymentMethodsInputPort {

    Page<PaymentMethod> listPaymentMethods(Pageable pageable);
}
