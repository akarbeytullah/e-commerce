package com.trendyol.ecommerce.shopping.manager.delivery;

import com.trendyol.ecommerce.shopping.model.ShoppingCart;

/**
 * @author bakar
 * @since 7.07.2019 13:16
 */
public interface DeliveryManagement {

    Double getDeliveryCost(ShoppingCart cart);
}
