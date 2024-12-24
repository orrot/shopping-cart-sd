package com.orrot.store.onlineuser.adapter.input.json;

import com.orrot.store.onlineuser.domain.model.Address;
import lombok.Builder;
import lombok.With;

@Builder(toBuilder = true)
@With
public record OnlineClientWrite(
        String name, Address address) {

}
