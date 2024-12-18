package com.orrot.store.cart.adapter.input;

import com.orrot.store.cart.adapter.input.json.PaymentMethodView;
import com.orrot.store.cart.adapter.input.json.mapper.PaymentMethodJsonViewMapper;
import com.orrot.store.cart.port.usecase.ListPaymentMethodsUseCase;
import com.orrot.store.common.rest.ResourcesURI;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ResourcesURI.PAYMENT_METHODS_URI)
@Tag(name = ResourcesURI.PAYMENT_METHODS_TAG)
@RequiredArgsConstructor
public class PaymentMethodRestAdapter {

    private final PaymentMethodJsonViewMapper mapper;
    private final ListPaymentMethodsUseCase listPaymentMethodsUseCase;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List all payment methods")
    public Page<PaymentMethodView> listPayments(@ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        // Query
        // Consider combine Query Params and Specifications for more dynamic queries
        return listPaymentMethodsUseCase.listPaymentMethods(pageable)
                .map(mapper::mapToView);
    }
}
