package com.orrot.store.onlineuser.adapter.output.jpa.entity;


import com.orrot.store.cart.adapter.output.jpa.entity.CartJpaEntity;
import com.orrot.store.common.jpa.BaseJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.util.Set;

@Getter
@Setter
@Entity(name = "online_client")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class OnlineClientJpaEntity extends BaseJpaEntity {

    @Serial
    private static final long serialVersionUID = 5897685426257081719L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "name")
    private String name;

    @Embedded
    private AddressJpaEntity address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "onlineClientOwner")
    private Set<CartJpaEntity> cart;

}
