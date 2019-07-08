package com.trendyol.ecommerce.shopping.manager.shopping;

import com.trendyol.ecommerce.shopping.model.Product;
import com.trendyol.ecommerce.shopping.model.ShoppingCart;
import com.trendyol.ecommerce.shopping.model.discount.coupon.Coupon;

import java.util.Optional;

/**
 * @author bakar
 * @since 7.07.2019 11:03
 */
public interface ShoppingCartManagement {

    void addItem(Product product, Long quantity, String user);

    Optional<ShoppingCart> getShoppingCartWithCustomerName(String customerName);

    Double totalAmountWithDiscount(ShoppingCart shoppingCart);

    Double getCampaignDiscount(ShoppingCart shoppingCart);

    Double getCouponDiscount(ShoppingCart shoppingCart);

    Double getDeliveryCost(ShoppingCart cart);

    boolean isCouponApplicable(ShoppingCart shoppingCart, Coupon coupon);

    void addCouponToCart(ShoppingCart shoppingCart, Coupon coupon);
}
