package com.trendyol.ecommerce.shopping.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author bakar
 * @since 6.07.2019 18:46
 */

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor(force = true)
public class ShoppingItem {

    @Id
    @SequenceGenerator(name = "genShoppingItemId", sequenceName = "SEQ_SHOPPING_ITEM_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genShoppingItemId")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "PRODUCT_ID", foreignKey = @ForeignKey(name = "FK_SHOPPING_ITEM_PRODUCT"))
    private final Product product;

    @Column(nullable = false)
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "SHOPPING_CART_ID", foreignKey = @ForeignKey(name = "FK_SHOPPING_ITEM_SHOPPING_CART"))
    private ShoppingCart shoppingCart;

    public Category getCategory() {
        return this.product.getCategory();
    }

    public ShoppingItem(Product product, Long quantity) {

        this.product = product;
        this.quantity = quantity;
    }

    public void incrementQuantity(Long incrementCount) {
        this.quantity = this.quantity + incrementCount;

    }
}
