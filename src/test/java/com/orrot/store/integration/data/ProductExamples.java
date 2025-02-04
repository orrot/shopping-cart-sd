package com.orrot.store.integration.data;

import com.orrot.store.product.adapter.input.json.ProductView;
import com.orrot.store.product.adapter.input.json.ProductWrite;

import java.math.BigDecimal;

// Data created for Integration tests only
public class ProductExamples {

    public static final String UPDATED_STRING = " Updated";

    public static ProductView dummyView() {
        return ProductView.builder()
                .id(1L)
                .name("Product IntegrationTest")
                .currentPrice(new BigDecimal(100))
                .description("Description of product")
                .build();
    }

    public static ProductWrite dummyToWrite() {
        return ProductWrite.builder()
                .name("Product IntegrationTest")
                .currentPrice(new BigDecimal(100))
                .description("Description of product")
                .build();
    }

    public static ProductView dummyView(Number id, ProductWrite dummyWrite) {
        return ProductView.builder()
                .id(id.longValue())
                .name(dummyWrite.name())
                .currentPrice(dummyWrite.currentPrice())
                .description(dummyWrite.description())
                .build();
    }

    public static ProductWrite updateAllFields(ProductWrite product) {
        return ProductWrite.builder()
                .name(product.name() + UPDATED_STRING)
                .currentPrice(product.currentPrice().add(new BigDecimal(100)))
                .description(product.description() + UPDATED_STRING)
                .build();
    }
}
