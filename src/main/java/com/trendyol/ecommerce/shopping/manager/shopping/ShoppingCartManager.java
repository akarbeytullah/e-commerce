package com.trendyol.ecommerce.shopping.manager.shopping;

import com.trendyol.ecommerce.shopping.manager.delivery.DeliveryManagement;
import com.trendyol.ecommerce.shopping.manager.discount.DiscountManagement;
import com.trendyol.ecommerce.shopping.model.Product;
import com.trendyol.ecommerce.shopping.model.ShoppingCart;
import com.trendyol.ecommerce.shopping.model.ShoppingItem;
import com.trendyol.ecommerce.shopping.model.discount.coupon.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author bakar
 * @since 6.07.2019 18:53
 */
@Service
@RequiredArgsConstructor
public class ShoppingCartManager implements ShoppingCartManagement {

    private static final double COUPON_DISCOUNT_VALUE_FOR_NO_COUPON_IN_CART = 0.0;

    private final ShoppingCartRepository shoppingCartRepository;

    private final DiscountManagement discountManagement;

    private final DeliveryManagement deliveryManagement;

    @Override
    public void addItem(Product product, Long quantity, String user) {

        final ShoppingCart shoppingCart = getShoppingCart(user);

        if (shoppingCart.isProductExist(product)) {
            shoppingCart.incrementQuantity(product, quantity);
            shoppingCartRepository.save(shoppingCart);
            return;
        }

        final ShoppingItem shoppingItem = new ShoppingItem(product, quantity);
        shoppingItem.setShoppingCart(shoppingCart);
        shoppingCart.getShoppingItems().add(shoppingItem);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public Optional<ShoppingCart> getShoppingCartWithCustomerName(String customerName) {
        return shoppingCartRepository.findByCustomerName(customerName);
    }

    @Override
    public Double totalAmountWithDiscount(ShoppingCart shoppingCart) {

        final double amountWithCampaignDiscount = shoppingCart.calculatePrice() - getCampaignDiscount(shoppingCart);

        return shoppingCart.isCouponExist() ?
                calculatePriceWithCoupon(shoppingCart, amountWithCampaignDiscount) : amountWithCampaignDiscount;
    }

    @Override
    public Double getCampaignDiscount(ShoppingCart shoppingCart) {
        return discountManagement.calculate(shoppingCart);
    }

    @Override
    public Double getCouponDiscount(ShoppingCart shoppingCart) {

        if (!shoppingCart.isCouponExist()) {
            return COUPON_DISCOUNT_VALUE_FOR_NO_COUPON_IN_CART;
        }

        final double amountAfterCampaignDiscount = shoppingCart.calculatePrice() - getCampaignDiscount(shoppingCart);
        return getCouponDiscount(shoppingCart, amountAfterCampaignDiscount);
    }

    @Override
    public Double getDeliveryCost(ShoppingCart cart) {
        return deliveryManagement.getDeliveryCost(cart);
    }

    @Override
    public boolean isCouponApplicable(ShoppingCart shoppingCart, Coupon coupon) {
        return totalAmountWithDiscount(shoppingCart) > coupon.getAmount();
    }

    @Override
    public void addCouponToCart(ShoppingCart shoppingCart, Coupon coupon) {
        shoppingCart.setCoupon(coupon);
        shoppingCartRepository.save(shoppingCart);
    }

    private double calculatePriceWithCoupon(ShoppingCart shoppingCart, double amountWithCampaignDiscount) {
        return amountWithCampaignDiscount - getCouponDiscount(shoppingCart, amountWithCampaignDiscount);
    }

    private double getCouponDiscount(ShoppingCart shoppingCart, double amountWithCampaignDiscount) {
        return (amountWithCampaignDiscount * shoppingCart.getCoupon().getDiscount()) / 100;
    }

    private ShoppingCart getShoppingCart(String user) {
        return getShoppingCartWithCustomerName(user).orElseGet(() -> createNewCart(user));
    }

    private ShoppingCart createNewCart(String user) {

        final ShoppingCart cart = new ShoppingCart();
        cart.setCustomerName(user);
        return shoppingCartRepository.save(cart);
    }
}
