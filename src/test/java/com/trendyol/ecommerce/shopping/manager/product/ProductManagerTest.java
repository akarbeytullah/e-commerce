package com.trendyol.ecommerce.shopping.manager.product;

import com.trendyol.ecommerce.shopping.model.Category;
import com.trendyol.ecommerce.shopping.model.Product;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author bakar
 * @since 7.07.2019 11:38
 */
class ProductManagerTest {

    @Test
    void when_AddCallsForProduct_Expect_RepositorySaveShouldBeCalled() {

        final ProductRepository repository = mock(ProductRepository.class);
        final ProductManager productManager = new ProductManager(repository);

        final String title = "Apple";
        final String price = "100";
        final Category category = new Category("Food");
        productManager.addProduct(title, price, category);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(repository).save(productCaptor.capture());
        final Product product = productCaptor.getValue();

        assertEquals(title, product.getTitle(), "title should match!");
        assertEquals(price, product.getPrice(), "price should match!");
        assertEquals(category, product.getCategory(), "category should match!");
    }

    @Test
    void when_getProductCalls_Expect_findByIdCallFromRepository() {

        final ProductRepository repository = mock(ProductRepository.class);
        final ProductManager productManager = new ProductManager(repository);

        final long productId = 1L;
        productManager.getProduct(productId);

        verify(repository).findById(productId);
    }
}