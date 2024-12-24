package com.orrot.store.onlineuser.adapter.output;

import com.orrot.store.common.jpa.BaseDomainRepository;
import com.orrot.store.onlineuser.adapter.output.jpa.OnlineClientJpaRepository;
import com.orrot.store.onlineuser.adapter.output.jpa.entity.OnlineClientJpaEntity;
import com.orrot.store.onlineuser.adapter.output.jpa.mapper.OnlineClientDomainMapper;
import com.orrot.store.onlineuser.domain.model.OnlineClient;
import com.orrot.store.onlineuser.port.output.OnlineClientOutputPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class OnlineClientRepository
        extends BaseDomainRepository<OnlineClient, OnlineClientJpaEntity, Long>
        implements OnlineClientOutputPort {

    public OnlineClientRepository(OnlineClientJpaRepository onlineClientJpaRepository,
                                  OnlineClientDomainMapper onlineClientDomainMapper) {
        super(onlineClientJpaRepository, onlineClientDomainMapper);
    }
}
