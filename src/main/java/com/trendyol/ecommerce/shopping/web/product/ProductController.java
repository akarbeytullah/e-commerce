package com.trendyol.ecommerce.shopping.web.product;

import com.trendyol.ecommerce.shopping.manager.category.CategoryManagement;
import com.trendyol.ecommerce.shopping.manager.product.ProductManagement;
import com.trendyol.ecommerce.shopping.model.Category;
import com.trendyol.ecommerce.shopping.web.NullValueExistInBodyException;
import com.trendyol.ecommerce.shopping.web.ValueNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author bakar
 * @since 6.07.2019 19:07
 */

@RestController
@RequestMapping("product")
@CrossOrigin
@RequiredArgsConstructor
public class ProductController {

    private final ProductManagement productManagement;

    private final CategoryManagement categoryManagement;

    @PostMapping(value = "/add/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@ PathVariable Long categoryId, @RequestBody Map<String, String> body) {

        if (isAnyNullValueForProduct(body)) {
            throw new NullValueExistInBodyException("Null value is present when adding new Product!");
        }

        productManagement.addProduct(body.get("title"), body.get("price"), getCategory(categoryId));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Category getCategory(Long categoryId) {

        return categoryManagement.getCategory(categoryId)
                .orElseThrow(() -> new ValueNotFoundException("Category is not found!"));
    }

    private boolean isAnyNullValueForProduct(Map<String, String> productInfo) {
        return productInfo.get("title") == null || productInfo.get("price") == null;
    }
}
