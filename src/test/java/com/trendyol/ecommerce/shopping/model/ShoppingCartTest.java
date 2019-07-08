package com.trendyol.ecommerce.shopping.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author bakar
 * @since 7.07.2019 13:45
 */
class ShoppingCartTest {

    private static final int NUMBER_OF_CATEGORY = 2;
    private static final int NUMBER_OF_FOOD = 4;

    @Test
    void when_getDistinctCountOfCategory_Expect_returnProperCount() {

        final ShoppingCart shoppingCart = new ShoppingCart();
        addFoodItemsToCart(shoppingCart);
        addSportItemsToCart(shoppingCart);

        assertEquals(NUMBER_OF_CATEGORY, shoppingCart.getDistinctCategoryCount().intValue(),
                "Distinct category count should be 2 for food and sport!");
    }

    @Test
    void when_getDistinctCountOfProduct_Expect_returnProperCount() {

        final ShoppingCart shoppingCart = new ShoppingCart();
        addFoodItemsToCart(shoppingCart);
        addSportItemsToCart(shoppingCart);

        assertEquals(NUMBER_OF_FOOD, shoppingCart.getDistinctProductsCount().intValue(),
                "Distinct product count should be 4 for food as 2(apple,banana)  and sport as 2(ball,socks)!");
    }

    @Test
    void when_calculatePriceCalls_Expect_returnProperPrice() {

        final ShoppingCart shoppingCart = new ShoppingCart();
        addFoodItemsToCart(shoppingCart);
        final Double calculatedPrice = 250.0;
        assertEquals(calculatedPrice, shoppingCart.calculatePrice(), "Prices should be matched!");
    }

    private void addFoodItemsToCart(ShoppingCart shoppingCart) {

        final Category foodCategory = new Category("Food");
        final Product appleProduct = new Product("Apple", "5", foodCategory);
        final Product bananaProduct = new Product("Banana", "20", foodCategory);

        final List<ShoppingItem> foodList =
                Arrays.asList(new ShoppingItem(appleProduct, 10L), new ShoppingItem(bananaProduct, 10L));

        shoppingCart.getShoppingItems().addAll(foodList);
    }

    private void addSportItemsToCart(ShoppingCart shoppingCart) {

        final Category sportCategory = new Category("Sport");
        final Product ballProduct = new Product("Ball", "5", sportCategory);
        final Product socksProduct = new Product("Socks", "20", sportCategory);

        final List<ShoppingItem> sportList =
                Arrays.asList(new ShoppingItem(ballProduct, 10L), new ShoppingItem(socksProduct, 10L));

        shoppingCart.getShoppingItems().addAll(sportList);
    }


}