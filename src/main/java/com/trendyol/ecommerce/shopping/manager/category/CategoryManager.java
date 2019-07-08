package com.trendyol.ecommerce.shopping.manager.category;

import com.trendyol.ecommerce.shopping.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author bakar
 * @since 6.07.2019 20:23
 */
@Service
@RequiredArgsConstructor
public class CategoryManager implements CategoryManagement {

    private final CategoryRepository categoryRepository;

    @Override
    public void addCategory(String name) {
        categoryRepository.save(new Category(name));
    }

    @Override
    public Optional<Category> getCategory(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public boolean isExist(String name) {
        return categoryRepository.findByName(name).isPresent();
    }
}
