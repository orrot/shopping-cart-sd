package com.orrot.store.onlineuser.port.usecase;

import com.orrot.store.onlineuser.domain.model.OnlineClient;
import com.orrot.store.onlineuser.domain.service.OnlineClientService;
import com.orrot.store.onlineuser.port.input.UpdateOnlineClientInputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateOnlineClientUseCase implements UpdateOnlineClientInputPort {

    private final OnlineClientService onlineClientService;

    @Override
    public void updateOnlineClient(OnlineClient onlineClient) {
        onlineClientService.update(onlineClient);
    }
}
