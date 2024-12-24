package com.orrot.store.onlineuser.adapter.input.json;

import com.orrot.store.onlineuser.domain.model.Address;
import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
public record OnlineClientView(
        Long id, String name, Address address) {

}
