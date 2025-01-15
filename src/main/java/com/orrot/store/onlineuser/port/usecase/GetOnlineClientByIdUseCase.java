package com.orrot.store.onlineuser.port.usecase;

import com.orrot.store.common.exception.UnExistingResourceException;
import com.orrot.store.onlineuser.domain.model.OnlineClient;
import com.orrot.store.onlineuser.domain.service.OnlineClientService;
import com.orrot.store.onlineuser.port.input.GetOnlineClientByIdInputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetOnlineClientByIdUseCase implements GetOnlineClientByIdInputPort {

    private final OnlineClientService onlineClientService;

    @Override
    public OnlineClient findById(Long onlineClientId) {
        return onlineClientService.findById(onlineClientId)
                .orElseThrow(() -> new UnExistingResourceException(
                        "Client ID '%d' does not exist".formatted(onlineClientId)));
    }
}
