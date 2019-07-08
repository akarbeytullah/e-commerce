package com.trendyol.ecommerce.shopping.manager.category;

import com.trendyol.ecommerce.shopping.model.Category;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author bakar
 * @since 6.07.2019 20:30
 */
class CategoryManagerTest {

    @Test
    void when_AddCalls_Expect_RepositorySaveShouldBeCalled() {

        final CategoryRepository repository = mock(CategoryRepository.class);
        final CategoryManager categoryManager = new CategoryManager(repository);

        categoryManager.addCategory("Food");

        verify(repository).save(Mockito.any(Category.class));
    }

    @Test
    void when_getCategoryCalls_Expect_RepositoryShouldBeCalledWithFindById() {

        final CategoryRepository repository = mock(CategoryRepository.class);
        final CategoryManager categoryManager = new CategoryManager(repository);

        final Long categoryId = 1L;
        categoryManager.getCategory(categoryId);

        verify(repository).findById(categoryId);
    }

    @Test
    void when_isCategoryExistCalls_Expect_RepositoryShouldBeCalledWithFindByName() {

        final CategoryRepository repository = mock(CategoryRepository.class);
        final CategoryManager categoryManager = new CategoryManager(repository);

        final String categoryName = "Food";
        categoryManager.isExist(categoryName);

        verify(repository).findByName(categoryName);
    }

}