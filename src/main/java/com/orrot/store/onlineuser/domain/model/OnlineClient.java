package com.orrot.store.onlineuser.domain.model;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class OnlineClient {

    private Long id;

    @NotEmpty(message = "Client name is required")
    @Column(name = "name")
    @ToString.Include
    private String name;

    @Valid
    private Address address;
}
