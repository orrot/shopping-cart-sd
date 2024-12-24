package com.orrot.store.onlineuser.domain.service;

import com.orrot.store.onlineuser.domain.model.OnlineClient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface OnlineClientService {

    OnlineClient create(@NotNull @Valid OnlineClient onlineClientToCreate);

    OnlineClient update(@NotNull @Valid OnlineClient onlineClientToUpdate);

    Optional<OnlineClient> findById(Long productId);

}
