package com.orrot.store.onlineuser.domain.service.impl;

import com.orrot.store.onlineuser.domain.model.OnlineClient;
import com.orrot.store.onlineuser.domain.service.OnlineClientService;
import com.orrot.store.onlineuser.port.output.OnlineClientOutputPort;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class OnlineClientServiceImpl implements OnlineClientService {

    private final OnlineClientOutputPort onlineClientOutputPort;

    @Override
    public OnlineClient create(@NotNull @Valid OnlineClient onlineClientToCreate) {

        return onlineClientOutputPort.create(onlineClientToCreate);
    }

    @Override
    public OnlineClient update(@NotNull @Valid OnlineClient onlineClientToUpdate) {

        return onlineClientOutputPort.update(
                onlineClientToUpdate.getId(), onlineClientToUpdate);
    }

    @Override
    public Optional<OnlineClient> findById(Long productId) {

        return onlineClientOutputPort.findById(productId);
    }
}
