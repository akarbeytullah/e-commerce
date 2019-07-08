package com.trendyol.ecommerce.shopping.manager.discount;

import com.trendyol.ecommerce.shopping.model.ShoppingCart;

/**
 * @author bakar
 * @since 7.07.2019 18:31
 */
public interface DiscountManagement {

    Double calculate(ShoppingCart shoppingCart);

}
