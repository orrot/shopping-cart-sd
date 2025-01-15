package com.orrot.store.onlineuser.adapter.input;

import com.orrot.store.common.rest.ResourcesURI;
import com.orrot.store.common.rest.json.IdentityId;
import com.orrot.store.onlineuser.adapter.input.json.OnlineClientWrite;
import com.orrot.store.onlineuser.adapter.input.json.mapper.OnlineClientJsonViewMapper;
import com.orrot.store.onlineuser.port.input.CreateOnlineClientInputPort;
import com.orrot.store.onlineuser.port.input.UpdateOnlineClientInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ResourcesURI.ONLINE_CLIENTS_URI)
@Tag(name = ResourcesURI.ONLINE_CLIENTS_TAG)
@RequiredArgsConstructor
public class OnlineClientWriteRestAdapter {

    private final OnlineClientJsonViewMapper mapper;

    private final CreateOnlineClientInputPort createOnlineClientInputPort;
    private final UpdateOnlineClientInputPort updateOnlineClientInputPort;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a new client of the store that buy online")
    public IdentityId create(
            @RequestBody OnlineClientWrite onlineClientWrite) {

        var onlineClient = mapper.mapToDomain(onlineClientWrite);
        var createdClient = createOnlineClientInputPort
                .createOnlineClient(onlineClient);
        return new IdentityId(createdClient.getId());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Updates an existing client of the store")
    public void update(
            @PathVariable("id") Long id,
            @RequestBody OnlineClientWrite onlineClientWrite) {

        var onlineClient = mapper.mapToDomain(onlineClientWrite);
        updateOnlineClientInputPort.updateOnlineClient(onlineClient.withId(id));
    }
}
