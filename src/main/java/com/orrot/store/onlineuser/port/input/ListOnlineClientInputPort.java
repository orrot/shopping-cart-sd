package com.orrot.store.onlineuser.port.input;

import com.orrot.store.onlineuser.domain.model.OnlineClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListOnlineClientInputPort {

    Page<OnlineClient> listOnlineClients(Pageable pageable);
}
