package com.orrot.store.shoppingcart.adapter.output.jpa.entity;

import com.orrot.store.common.BaseJpaEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity(name = "cart")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class CartJpaEntity extends BaseJpaEntity {

    @Serial
    private static final long serialVersionUID = -5512687501901452445L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_code", referencedColumnName = "code")
    private PaymentMethodJpaEntity paymentMethod;

    @Column(name = "associated_user_id")
    @ToString.Include
    private Long associatedUserId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private List<CartItemJpaEntity> cartItems = new ArrayList<>();

    public void setCartItems(Collection<CartItemJpaEntity> cartItems) {
        this.cartItems.clear();
        cartItems.forEach(
                cartItemJpaEntity -> cartItemJpaEntity.setCart(this));
        this.cartItems.addAll(cartItems);

    }
}
