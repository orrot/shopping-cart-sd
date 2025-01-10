package com.orrot.store.onlineuser.port.input;

import com.orrot.store.onlineuser.adapter.output.OnlineClientRepository;
import com.orrot.store.onlineuser.domain.model.OnlineClient;
import com.orrot.store.onlineuser.port.usecase.ListOnlineClientUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListOnlineClientsInputPort implements ListOnlineClientUseCase {

    private final OnlineClientRepository onlineClientRepository;

    @Override
    public Page<OnlineClient> listOnlineClients(Pageable pageable) {
        return onlineClientRepository.findAll(pageable);
    }
}
