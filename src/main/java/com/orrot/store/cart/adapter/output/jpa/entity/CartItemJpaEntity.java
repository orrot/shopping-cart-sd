package com.orrot.store.cart.adapter.output.jpa.entity;

import com.orrot.store.product.adapter.output.jpa.entity.ProductJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Optional;

@Getter
@Setter
@Entity(name = "CartItem")
@Table(name = "cart_item")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString(onlyExplicitlyIncluded = true)
@IdClass(CartItemJpaId.class)
@NamedEntityGraph(name = "Cart.withItems",
        attributeNodes = {
                @NamedAttributeNode("product")
        }
)
public class CartItemJpaEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 5897685426257081719L;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private CartJpaEntity cart;

    @Id
    @Column(name = "product_id")
    @EqualsAndHashCode.Include
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id", updatable = false, insertable = false)
    private ProductJpaEntity product;

    @Builder.Default
    private int quantity = 1;

    public String getProductName() {
        return Optional.ofNullable(product)
                .map(ProductJpaEntity::getName)
                .orElse(null);
    }

    public BigDecimal getCurrentPrice() {
        return Optional.ofNullable(product)
                .map(ProductJpaEntity::getCurrentPrice)
                .orElse(null);
    }

}
