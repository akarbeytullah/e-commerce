package com.trendyol.ecommerce.shopping.manager.shopping;

import com.trendyol.ecommerce.shopping.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author bakar
 * @since 7.07.2019 11:04
 */
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByCustomerName(String name);
}
