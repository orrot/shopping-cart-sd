package com.orrot.store.integration.data;

import com.orrot.store.cart.adapter.input.json.CartWrite;
import com.orrot.store.common.rest.json.IdentityCode;

public class CartExamples {

    public static CartWrite dummyToWrite() {
        return CartWrite.builder()
                .paymentMethod(new IdentityCode("CASH"))
                .onlineClientOwnerId(null)
                .build();
    }

    public static CartWrite update(Long onlineClientId, String paymentMethodCode) {
        return CartWrite.builder()
                .onlineClientOwnerId(onlineClientId)
                .paymentMethod(new IdentityCode(paymentMethodCode))
                .build();
    }
}