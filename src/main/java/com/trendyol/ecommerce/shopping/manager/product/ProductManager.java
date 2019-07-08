package com.trendyol.ecommerce.shopping.manager.product;

import com.trendyol.ecommerce.shopping.model.Category;
import com.trendyol.ecommerce.shopping.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author bakar
 * @since 6.07.2019 19:16
 */
@Service
@RequiredArgsConstructor
public class ProductManager implements ProductManagement {

    private final ProductRepository productRepository;

    @Override
    public void addProduct(String title, String price, Category category) {
        productRepository.save(createNewProduct(title, price, category));
    }

    private Product createNewProduct(String title, String price, Category category) {
        return new Product(title, price, category);
    }

    @Override
    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }
}
