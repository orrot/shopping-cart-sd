package com.orrot.store.onlineuser.adapter.output.jpa.mapper;

import com.orrot.store.common.jpa.BaseDomainMapper;
import com.orrot.store.onlineuser.adapter.output.jpa.entity.OnlineClientJpaEntity;
import com.orrot.store.onlineuser.domain.model.OnlineClient;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface OnlineClientDomainMapper extends BaseDomainMapper<OnlineClient, OnlineClientJpaEntity> {

}
