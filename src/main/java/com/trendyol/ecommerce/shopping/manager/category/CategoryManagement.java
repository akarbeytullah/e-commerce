package com.trendyol.ecommerce.shopping.manager.category;

import com.trendyol.ecommerce.shopping.model.Category;

import java.util.Optional;

/**
 * @author bakar
 * @since 6.07.2019 20:24
 */
public interface CategoryManagement {

    Optional<Category> getCategory(Long id);

    void addCategory(String name);

    boolean isExist(String name);
}
