package com.orrot.store.onlineuser.port.usecase;

import com.orrot.store.onlineuser.domain.model.OnlineClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListOnlineClientUseCase {

    Page<OnlineClient> listOnlineClients(Pageable pageable);
}
