package com.orrot.store.integration.data;


import com.orrot.store.onlineuser.adapter.input.json.OnlineClientView;
import com.orrot.store.onlineuser.adapter.input.json.OnlineClientWrite;
import com.orrot.store.onlineuser.domain.model.Address;

public class OnlineClientExamples {

    public static final String UPDATED_STRING = " Updated";

    public static OnlineClientWrite dummyToWrite() {
        return OnlineClientWrite.builder()
                .name("Test Client")
                .address(Address.builder()
                        .city("Shinganshina")
                        .line1("Distrito Shinganshina")
                        .line2("Cerca al Muro")
                        .zipCode("4500")
                        .build())
                .build();
    }

    public static OnlineClientView dummyView(Number id, OnlineClientWrite onlineClientWrite) {
        return OnlineClientView.builder()
                .id(id.longValue())
                .name(onlineClientWrite.name())
                .address(onlineClientWrite.address())
                .build();
    }

    public static OnlineClientWrite updateAllFields(OnlineClientWrite onlineClientWrite) {
        return onlineClientWrite.toBuilder()
                .name(onlineClientWrite.name() + UPDATED_STRING)
                .address(
                        Address.builder()
                                .city(onlineClientWrite.address().city() + UPDATED_STRING)
                                .line1(onlineClientWrite.address().line1()  + UPDATED_STRING)
                                .line2(onlineClientWrite.address().line2() + UPDATED_STRING)
                                .zipCode(onlineClientWrite.address().zipCode() + UPDATED_STRING)
                                .build())
                .build();
    }
}