package com.orrot.store.shoppingcart.adapter.input;

import com.orrot.store.shoppingcart.adapter.input.model.PaymentMethodView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/payment-methods")
@Tag(name = "Payment Methods")
public class PaymentMethodRestAdapter {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List all payment methods")
    public Page<PaymentMethodView> listPayments(@ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        return new PageImpl<>(List.of());
    }
}
