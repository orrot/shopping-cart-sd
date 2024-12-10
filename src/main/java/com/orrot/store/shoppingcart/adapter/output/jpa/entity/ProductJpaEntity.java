package com.orrot.store.shoppingcart.adapter.output.jpa.entity;


import com.orrot.store.common.BaseJpaEntity;
import jakarta.persistence.Column;
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
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class ProductJpaEntity extends BaseJpaEntity {

    @Serial
    private static final long serialVersionUID = 5897685426257081719L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;

    @Column(name = "name")
    @ToString.Include
    private String name;

    @Column(name = "current_price")
    @ToString.Include
    private BigDecimal currentPrice;

    @Column(name = "description")
    private String description;

    // TODO Orlando uncomment this to test
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    @EqualsAndHashCode.Exclude
    private Set<CartItemJpaEntity> cartItems;

}
