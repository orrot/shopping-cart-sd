package com.orrot.store.shoppingcart.adapter.input;

import com.orrot.store.shoppingcart.adapter.input.model.ProductView;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/products")
@Tag(name = "Products")
public class ProductRestAdapter {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductView> listProducts(@ParameterObject Pageable pageable) {
        return new PageImpl<>(List.of());
    }
}
