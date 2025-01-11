package com.orrot.store.cart.adapter.output.jpa.entity;


import com.orrot.store.common.jpa.BaseJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Entity(name = "payment_method")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class PaymentMethodJpaEntity extends BaseJpaEntity {

    @Serial
    private static final long serialVersionUID = 2832966567506622256L;

    @Id
    @Column(name = "code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private String code;

    @Column(name = "name")
    @ToString.Include
    private String name;

    @Column(name = "percentage_fee")
    private BigDecimal percentageFee;

    @Column(name = "fixed_fee")
    private BigDecimal fixedFee;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentMethod")
    private Set<CartJpaEntity> carts;

}
