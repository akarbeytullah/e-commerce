package com.trendyol.ecommerce.shopping.manager.product;

import com.trendyol.ecommerce.shopping.model.Category;
import com.trendyol.ecommerce.shopping.model.Product;

import java.util.Optional;

/**
 * @author bakar
 * @since 6.07.2019 19:14
 */
public interface ProductManagement {

    void addProduct(String title, String price, Category category);

    Optional<Product> getProduct(Long Id);
}
