package com.orrot.store.cart.adapter.output;

import com.orrot.store.cart.adapter.output.jpa.PaymentMethodJpaRepository;
import com.orrot.store.cart.adapter.output.jpa.mapper.PaymentMethodDomainMapper;
import com.orrot.store.cart.domain.model.PaymentMethod;
import com.orrot.store.cart.port.output.PaymentMethodOutputPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
@Validated
public class PaymentMethodRepository implements PaymentMethodOutputPort {

    private final PaymentMethodDomainMapper paymentMethodDomainMapper;
    private final PaymentMethodJpaRepository paymentMethodJpaRepository;

    @Override
    public boolean exists(PaymentMethod paymentMethod) {
        return Optional.ofNullable(paymentMethod)
                .map(PaymentMethod::getCode)
                .map(paymentMethodJpaRepository::existsById)
                .orElse(false);
    }
}
