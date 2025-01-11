package com.orrot.store.onlineuser.port.usecase;

import com.orrot.store.onlineuser.domain.model.OnlineClient;

public interface GetOnlineClientByIdUseCase {

    OnlineClient findById(Long id);
}
