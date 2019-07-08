package com.trendyol.ecommerce.shopping.manager.product;

import com.trendyol.ecommerce.shopping.model.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * @author bakar
 * @since 6.07.2019 19:12
 */
public interface ProductRepository extends CrudRepository<Product, Long> {
}
