package com.orrot.store.onlineuser.port.usecase;

import com.orrot.store.onlineuser.domain.model.OnlineClient;
import com.orrot.store.onlineuser.port.input.ListOnlineClientInputPort;
import com.orrot.store.onlineuser.port.output.OnlineClientOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListOnlineClientsUseCase implements ListOnlineClientInputPort {

    private final OnlineClientOutputPort onlineClientOutputPort;

    @Override
    public Page<OnlineClient> listOnlineClients(Pageable pageable) {
        return onlineClientOutputPort.findAll(pageable);
    }
}
