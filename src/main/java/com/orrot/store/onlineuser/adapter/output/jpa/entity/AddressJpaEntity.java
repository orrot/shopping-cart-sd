package com.orrot.store.onlineuser.adapter.output.jpa.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class AddressJpaEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 5897685426257081719L;

    @Column(name = "address_line1")
    @NotNull
    private String line1;

    @Column(name = "address_line2")
    private String line2;

    @Column(name = "address_city")
    private String city;

    @Column(name = "address_zip_code")
    private String zipCode;

}
