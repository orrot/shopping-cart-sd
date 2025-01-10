package com.orrot.store.cart.adapter.output;

import com.orrot.store.cart.adapter.output.jpa.PaymentMethodJpaRepository;
import com.orrot.store.cart.adapter.output.jpa.entity.PaymentMethodJpaEntity;
import com.orrot.store.cart.adapter.output.jpa.mapper.PaymentMethodDomainMapper;
import com.orrot.store.cart.domain.model.PaymentMethod;
import com.orrot.store.cart.port.output.PaymentMethodOutputPort;
import com.orrot.store.common.jpa.BaseDomainRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Repository
@Transactional
@Validated
public class PaymentMethodRepository
        extends BaseDomainRepository<PaymentMethod, PaymentMethodJpaEntity, String>
        implements PaymentMethodOutputPort {

    public PaymentMethodRepository(PaymentMethodJpaRepository jpaRepository,
                                   PaymentMethodDomainMapper domainMapper) {
        super(jpaRepository, domainMapper);
    }
}
