package com.orrot.store.onlineuser.port.output;

import com.orrot.store.onlineuser.domain.model.OnlineClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OnlineClientOutputPort {

    Optional<OnlineClient> findById(Long id);

    Page<OnlineClient> findAll(Pageable pageable);
}
