package com.orrot.store.onlineuser.port.usecase;

import com.orrot.store.onlineuser.domain.model.OnlineClient;

public interface CreateOnlineClientUseCase {

    OnlineClient createOnlineClient(OnlineClient onlineClient);
}
