package com.trendyol.ecommerce.shopping.manager.delivery;

import com.trendyol.ecommerce.shopping.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author bakar
 * @since 7.07.2019 13:17
 */

@Service
public class DeliveryManager implements DeliveryManagement {

    @Value("${deliver.costPerDelivery}")
    private Double COST_PER_DELIVERY;

    @Value("${deliver.costPerProduct}")
    private Double COST_PER_PRODUCT;

    @Value("${deliver.fixedCost}")
    private Double FIXED_COST;

    @Override
    public Double getDeliveryCost(ShoppingCart cart) {

        return (COST_PER_DELIVERY * cart.getDistinctCategoryCount())
                + (COST_PER_PRODUCT * cart.getDistinctProductsCount()) + FIXED_COST;
    }
}
