package com.orrot.store.integration;

import com.google.gson.Gson;
import com.orrot.store.AbstractContainerBaseTest;
import com.orrot.store.cart.adapter.input.json.PaymentMethodView;
import com.orrot.store.common.JsonUtils;
import com.orrot.store.common.rest.ResourcesURI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("During Payment Method Listing")
public class ListPaymentMethodIntegrationTest extends AbstractContainerBaseTest {

    private static final Gson gson = new Gson();

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("When listing payment methods")
    class WhenListingPaymentMethods {

        @Test
        @DisplayName("Should return OK status and the page of payment methods")
        @Sql(value = "/sql/paymentmethod/default_list.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @Sql(value = "/sql/paymentmethod/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        void shouldReturnOkAndThePageOfPaymentMethods() throws Exception {

            // When / Then
            var result = mockMvc.perform(get(ResourcesURI.PAYMENT_METHODS_URI)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.totalElements").value(2))
                    .andReturn();

            // Assert
            var paymentMethods = JsonUtils.extractListFrom(result, "$.content", PaymentMethodView.class);

            assertThat(paymentMethods)
                    .as("All the fields for all the payment methods should be non-null")
                    .flatExtracting(PaymentMethodView::code, PaymentMethodView::name)
                    .doesNotContainNull();
        }

    }
}