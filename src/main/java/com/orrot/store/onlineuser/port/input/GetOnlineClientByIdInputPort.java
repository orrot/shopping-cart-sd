package com.orrot.store.onlineuser.port.input;

import com.orrot.store.onlineuser.domain.model.OnlineClient;

public interface GetOnlineClientByIdInputPort {

    OnlineClient findById(Long id);
}
