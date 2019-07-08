package com.trendyol.ecommerce.shopping.web.product;

import com.trendyol.ecommerce.shopping.manager.category.CategoryManagement;
import com.trendyol.ecommerce.shopping.manager.product.ProductManagement;
import com.trendyol.ecommerce.shopping.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author bakar
 * @since 6.07.2019 19:30
 */

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductManagement productManagement;

    @Mock
    private CategoryManagement categoryManagement;

    @BeforeEach
    void setUp() {

        ProductController controller = new ProductController(productManagement, categoryManagement);
        mockMvc = MockMvcBuilders.
                standaloneSetup(controller)
                .setControllerAdvice(ProductRestResponseEntityExceptionHandler.class)
                .build();
    }

    @Test
    void when_ExceptionThrowsWithNullPriceValue_Expect_ControllerAdviceShouldReturnProperResponse() throws Exception {

        String requestJson = "{\"title\":\"Apple\"}";

        mockMvc.perform(post("/product/add/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void when_AddProductCalls_Expect_ProductManagementShouldSaveProduct() throws Exception {

        final String title = "Apple";
        final String price = "100";
        String requestJson = "{\"title\":\"" + title + "\", \"price\":\"" + price + "\"}";
        final String categoryId = "1";

        final Optional<Category> categoryOfFood = Optional.of(new Category("Food"));
        when(categoryManagement.getCategory(Long.valueOf(categoryId))).thenReturn(categoryOfFood);

        mockMvc.perform(post("/product/add/" + categoryId)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().is2xxSuccessful());

        verify(productManagement).addProduct(title, price, categoryOfFood.get());
    }

}