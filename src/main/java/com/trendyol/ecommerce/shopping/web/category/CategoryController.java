package com.trendyol.ecommerce.shopping.web.category;

import com.trendyol.ecommerce.shopping.manager.category.CategoryManagement;
import com.trendyol.ecommerce.shopping.web.DataAlreadyDefinedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author bakar
 * @since 6.07.2019 19:08
 */
@RestController
@RequestMapping("category")
@CrossOrigin
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryManagement categoryManagement;

    @PostMapping(value = "/add/{categoryName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@PathVariable String categoryName) {

        if (categoryManagement.isExist(categoryName)) {
            throw new DataAlreadyDefinedException("Category is already defined!");
        }

        categoryManagement.addCategory(categoryName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
