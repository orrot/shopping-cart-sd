package com.orrot.store.onlineuser.adapter.input;

import com.orrot.store.common.rest.ResourcesURI;
import com.orrot.store.common.rest.json.IdentityId;
import com.orrot.store.onlineuser.adapter.input.json.OnlineClientView;
import com.orrot.store.onlineuser.adapter.input.json.OnlineClientWrite;
import com.orrot.store.onlineuser.adapter.input.json.mapper.OnlineClientJsonViewMapper;
import com.orrot.store.onlineuser.port.usecase.CreateOnlineClientUseCase;
import com.orrot.store.onlineuser.port.usecase.ListOnlineClientUseCase;
import com.orrot.store.onlineuser.port.usecase.UpdateOnlineClientUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ResourcesURI.ONLINE_CLIENT_URI)
@Tag(name = ResourcesURI.ONLINE_CLIENT_TAG)
@RequiredArgsConstructor
public class OnlineClientRestAdapter {

    private final OnlineClientJsonViewMapper mapper;

    private final ListOnlineClientUseCase listOnlineClientUseCase;
    private final CreateOnlineClientUseCase createOnlineClientUseCase;
    private final UpdateOnlineClientUseCase updateOnlineClientUseCase;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lists all the clients of the store that buy online")
    public Page<OnlineClientView> list(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {

        return listOnlineClientUseCase.listOnlineClients(pageable)
                .map(mapper::mapToView);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a new client of the store that buy online")
    public IdentityId create(
            @RequestBody OnlineClientWrite onlineClientWrite) {

        var onlineClient = mapper.mapToDomain(onlineClientWrite);
        var createdClient = createOnlineClientUseCase
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
        updateOnlineClientUseCase.updateOnlineClient(
                onlineClient.toBuilder()
                        .id(id)
                        .build());
    }
}
