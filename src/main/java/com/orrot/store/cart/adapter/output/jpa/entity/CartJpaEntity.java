package com.orrot.store.cart.adapter.output.jpa.entity;

import com.orrot.store.common.jpa.BaseJpaEntity;
import com.orrot.store.onlineuser.adapter.output.jpa.entity.OnlineClientJpaEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity(name = "cart")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@NamedEntityGraph(name = "Cart.fullInfo",
        attributeNodes = {
                @NamedAttributeNode("paymentMethod"),
                @NamedAttributeNode(value = "items", subgraph = "items.withProduct") // Just One Collection loaded, NO MORE
        },
        subgraphs = {
                @NamedSubgraph(name = "items.withProduct",
                        attributeNodes = {
                                @NamedAttributeNode("product")
                        }
                )
        })
public class CartJpaEntity extends BaseJpaEntity {

    @Serial
    private static final long serialVersionUID = -5512687501901452445L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_code", referencedColumnName = "code")
    private PaymentMethodJpaEntity paymentMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "online_client_owner_id", referencedColumnName = "id")
    private OnlineClientJpaEntity onlineClientOwner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CartItemJpaEntity> items = new ArrayList<>();

    public void setItems(Collection<CartItemJpaEntity> items) {
        this.items.clear();
        items.forEach(
                cartItemJpaEntity -> cartItemJpaEntity.setCart(this));
        this.items.addAll(items);

    }
}
