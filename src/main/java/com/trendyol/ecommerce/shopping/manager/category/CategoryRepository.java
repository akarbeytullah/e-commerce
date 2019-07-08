package com.trendyol.ecommerce.shopping.manager.category;

import com.trendyol.ecommerce.shopping.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author bakar
 * @since 6.07.2019 20:24
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByName(String name);
}
