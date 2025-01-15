package com.orrot.store.onlineuser.port.input;

import com.orrot.store.onlineuser.domain.model.OnlineClient;

public interface CreateOnlineClientInputPort {

    OnlineClient createOnlineClient(OnlineClient onlineClient);
}
