package com.orrot.store.cart.port.output;

import com.orrot.store.cart.domain.model.PaymentMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentMethodOutputPort {

    Page<PaymentMethod> findAll(Pageable pageable);

    boolean existsById(String code);
}
