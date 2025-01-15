package com.orrot.store.onlineuser.domain.model;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.With;

@Getter
@Setter
@AllArgsConstructor
@With
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
    @NotNull(message = "Address is mandatory")
    private Address address;

}
