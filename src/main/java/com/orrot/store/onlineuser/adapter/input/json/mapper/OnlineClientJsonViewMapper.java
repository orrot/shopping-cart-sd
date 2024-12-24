package com.orrot.store.onlineuser.adapter.input.json.mapper;

import com.orrot.store.onlineuser.adapter.input.json.OnlineClientView;
import com.orrot.store.onlineuser.adapter.input.json.OnlineClientWrite;
import com.orrot.store.onlineuser.domain.model.OnlineClient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OnlineClientJsonViewMapper {

    OnlineClientView mapToView(OnlineClient domain);

    OnlineClient mapToDomain(OnlineClientWrite jsonWrite);

}
