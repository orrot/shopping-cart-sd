package com.orrot.store.onlineuser.port.input;

import com.orrot.store.onlineuser.domain.model.OnlineClient;
import com.orrot.store.onlineuser.domain.service.OnlineClientService;
import com.orrot.store.onlineuser.port.usecase.CreateOnlineClientUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateOnlineClientInputPort implements CreateOnlineClientUseCase {

    private final OnlineClientService onlineClientService;

    @Override
    public OnlineClient createOnlineClient(OnlineClient onlineClient) {
        return onlineClientService.create(onlineClient);
    }
}
