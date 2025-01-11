package com.orrot.store.onlineuser.adapter.input;

import com.orrot.store.common.rest.ResourcesURI;
import com.orrot.store.onlineuser.adapter.input.json.OnlineClientView;
import com.orrot.store.onlineuser.adapter.input.json.mapper.OnlineClientJsonViewMapper;
import com.orrot.store.onlineuser.port.usecase.GetOnlineClientByIdUseCase;
import com.orrot.store.onlineuser.port.usecase.ListOnlineClientUseCase;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(ResourcesURI.ONLINE_CLIENTS_URI)
@Tag(name = ResourcesURI.ONLINE_CLIENTS_TAG)
@RequiredArgsConstructor
public class OnlineClientReadRestAdapter {

    private final OnlineClientJsonViewMapper mapper;
    private final ListOnlineClientUseCase listOnlineClientUseCase;
    private final GetOnlineClientByIdUseCase getOnlineClientByIdUseCase;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lists all the clients of the store that buy online")
    public Page<OnlineClientView> list(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {

        return listOnlineClientUseCase.listOnlineClients(pageable)
                .map(mapper::mapToView);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get clients that buy online by ID")
    public OnlineClientView findById(@PathVariable("id") Long id) {
        return Optional.of(id)
                .map(getOnlineClientByIdUseCase::findById)
                .map(mapper::mapToView)
                .orElseThrow();
    }
}
