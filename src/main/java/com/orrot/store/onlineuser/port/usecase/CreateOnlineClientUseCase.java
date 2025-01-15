package com.orrot.store.onlineuser.port.usecase;

import com.orrot.store.onlineuser.domain.model.OnlineClient;
import com.orrot.store.onlineuser.domain.service.OnlineClientService;
import com.orrot.store.onlineuser.port.input.CreateOnlineClientInputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateOnlineClientUseCase implements CreateOnlineClientInputPort {

    private final OnlineClientService onlineClientService;

    @Override
    public OnlineClient createOnlineClient(OnlineClient onlineClient) {
        return onlineClientService.create(onlineClient);
    }
}
