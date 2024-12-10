package com.orrot.store.shoppingcart.adapter.output.jpa.entity;


import com.orrot.store.common.BaseJpaEntity;
import com.orrot.store.shoppingcart.domain.model.FeeCalculationMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Entity(name = "payment")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class PaymentMethodJpaEntity extends BaseJpaEntity {

    @Serial
    private static final long serialVersionUID = 2832966567506622256L;

    @Id
    @Column(name = "code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private String code;

    @Column(name = "name")
    @ToString.Include
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "fee_calc_method")
    private FeeCalculationMethod feeCalcMethod;

    @Column(name = "fee_value")
    private BigDecimal feeValue;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentMethod")
    @EqualsAndHashCode.Exclude
    private Set<CartJpaEntity> cart;

}
