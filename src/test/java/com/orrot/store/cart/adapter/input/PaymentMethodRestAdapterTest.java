package com.orrot.store.cart.adapter.input;

import com.google.gson.Gson;
import com.orrot.store.cart.adapter.input.json.mapper.PaymentMethodJsonViewMapper;
import com.orrot.store.cart.adapter.input.json.mapper.PaymentMethodJsonViewMapperImpl;
import com.orrot.store.cart.domain.model.PaymentMethod;
import com.orrot.store.cart.port.usecase.ListPaymentMethodsUseCase;
import com.orrot.store.common.podam.MockerFactory;
import com.orrot.store.config.web.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PaymentMethodRestAdapter.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Import({PaymentMethodJsonViewMapperImpl.class, SecurityConfig.class})
class PaymentMethodRestAdapterTest {

    private static final Gson gson = new Gson();

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private PaymentMethodJsonViewMapper mapper;

    @MockBean
    private ListPaymentMethodsUseCase listPaymentMethodsUseCase;

    @Nested
    @DisplayName("When listing payment methods")
    class WhenListingPaymentMethods {

        @Test
        @DisplayName("Should return ok status and a list of payment methods")
        void shouldReturnOkStatusAndListOfPaymentMethods() throws Exception {
            var paymentMethod = MockerFactory.createDummy(PaymentMethod.class);
            var pageable = PageRequest.of(0, 20);

            given(listPaymentMethodsUseCase.listPaymentMethods(eq(pageable)))
                    .willReturn(new PageImpl<>(List.of(paymentMethod), pageable, 1));

            mockMvc.perform(get("/v1/payment-methods")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.totalElements").value(1))
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content[0].code").value(paymentMethod.getCode()))
                    .andExpect(jsonPath("$.content[0].name").value(paymentMethod.getName()))
                    .andReturn();
        }
    }
}