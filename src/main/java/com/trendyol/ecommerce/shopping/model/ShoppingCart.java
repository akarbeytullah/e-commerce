package com.trendyol.ecommerce.shopping.model;

import com.trendyol.ecommerce.shopping.model.discount.coupon.Coupon;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author bakar
 * @since 6.07.2019 17:59
 */

@Entity
@Table
@Getter
@Setter
public class ShoppingCart {

    private static final String QUEST = "QUEST";

    @Id
    @SequenceGenerator(name = "genShoppingCartId", sequenceName = "SEQ_SHOPPING_CART_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genShoppingCartId")
    private Long id;

    @Column(nullable = false)
    private String customerName = QUEST;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "shoppingCart")
    private List<ShoppingItem> shoppingItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "COUPON_ID", foreignKey = @ForeignKey(name = "FK_SHOPPING_CART_COUPON"))
    private Coupon coupon;

    public Map<Category, ArrayList<ShoppingItem>> getCategoryItems() {

        final Map<Category, ArrayList<ShoppingItem>> categoryItems = fillCategoryHashMap();
        this.shoppingItems.forEach(shoppingItem -> {

            final ArrayList<ShoppingItem> products = categoryItems.get(shoppingItem.getCategory());
            products.add(shoppingItem);
            categoryItems.put(shoppingItem.getCategory(), products);
        });

        return categoryItems;
    }

    private Map<Category, ArrayList<ShoppingItem>> fillCategoryHashMap() {
        return this.shoppingItems
                .stream()
                .map(ShoppingItem::getCategory)
                .collect(Collectors.toMap(o -> o, o -> new ArrayList<>(), (oldValue, newValue) -> oldValue));
    }

    public Integer getDistinctProductsCount() {

        return this.shoppingItems
                .stream()
                .map(ShoppingItem::getProduct)
                .collect(Collectors.toSet())
                .size();
    }

    public Integer getDistinctCategoryCount() {

        return this.shoppingItems
                .stream()
                .map(ShoppingItem::getCategory)
                .collect(Collectors.toSet())
                .size();
    }

    public boolean isProductExist(Product product) {

        return shoppingItems
                .stream()
                .anyMatch(shoppingItem -> shoppingItem.getProduct().getId().equals(product.getId()));
    }

    public void incrementQuantity(Product product, Long quantity) {

        shoppingItems
                .stream()
                .filter(shoppingItem -> shoppingItem.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product does not match!"))
                .incrementQuantity(quantity);
    }

    public Double calculatePrice() {

        return this.getShoppingItems()
                .stream().mapToDouble(items -> items.getQuantity() * Long.valueOf(items.getProduct().getPrice()))
                .sum();
    }

    public boolean isCouponExist() {
        return this.coupon != null;
    }
}
