package com.trendyol.ecommerce.shopping.web.category;

import com.trendyol.ecommerce.shopping.manager.category.CategoryManagement;
import com.trendyol.ecommerce.shopping.web.DataAlreadyDefinedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author bakar
 * @since 6.07.2019 22:14
 */
@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CategoryManagement categoryManagement;

    @BeforeEach
    void setUp() {

        CategoryController controller = new CategoryController(categoryManagement);
        mockMvc = MockMvcBuilders.
                standaloneSetup(controller)
                .setControllerAdvice(CategoryRestResponseEntityExceptionHandler.class)
                .build();
    }

    @Test
    void when_ExceptionThrowsWithAlreadyDefinedCategory_Expect_ControllerAdviceShouldReturnProperResponse() throws Exception {

        final String categoryName = "Food";
        doThrow(DataAlreadyDefinedException.class).when(categoryManagement).isExist(categoryName);

        mockMvc.perform(post("/category/add/" + categoryName)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    void when_AddCategoryCalls_Expect_CategoryManagementShouldSaveCategory() throws Exception {

        final String categoryName = "Sport";
        when(categoryManagement.isExist(categoryName)).thenReturn(false);

        mockMvc.perform(post("/category/add/" + categoryName)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is2xxSuccessful());

        verify(categoryManagement).addCategory(categoryName);
    }

}