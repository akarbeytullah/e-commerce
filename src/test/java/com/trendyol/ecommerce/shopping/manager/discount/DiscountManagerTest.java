package com.trendyol.ecommerce.shopping.manager.discount;

import com.trendyol.ecommerce.shopping.manager.campaign.AmountRepository;
import com.trendyol.ecommerce.shopping.manager.campaign.RateRepository;
import com.trendyol.ecommerce.shopping.model.Category;
import com.trendyol.ecommerce.shopping.model.Product;
import com.trendyol.ecommerce.shopping.model.ShoppingCart;
import com.trendyol.ecommerce.shopping.model.ShoppingItem;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author bakar
 * @since 7.07.2019 23:47
 */
class DiscountManagerTest {

    private static final Double DISCOUNT_TO_BE_CALCULATED = 200.0;

    @Test
    void when_calculateDiscountCalls_expect_returnProperDiscountRate() {

        AmountRepository amountRepository = mock(AmountRepository.class);
        RateRepository rateRepository = mock(RateRepository.class);
        final DiscountManager discountManager = new DiscountManager(amountRepository, rateRepository);

        final ShoppingCart shoppingCart = createShoppingCart();

        when(amountRepository.findMaxAmountCampaign(Mockito.any(), Mockito.any())).thenReturn(Optional.of(100.0));
        when(rateRepository.findMaxRateCampaign(Mockito.any(), Mockito.any())).thenReturn(Optional.of(20.0));

        assertEquals(DISCOUNT_TO_BE_CALCULATED, discountManager.calculate(shoppingCart),
                "Calculated discount should  be the same!");
    }

    private ShoppingCart createShoppingCart() {

        final ShoppingCart shoppingCart = new ShoppingCart();

        final Category foodCategory = new Category("Food");
        final Product productApple = new Product("Apple", "5", foodCategory);
        final Product productBanana = new Product("Banana", "5", foodCategory);
        final Product productBall = new Product("Ball", "20", new Category("Sport"));

        shoppingCart.getShoppingItems().add(new ShoppingItem(productApple, 5L));
        shoppingCart.getShoppingItems().add(new ShoppingItem(productBanana, 1L));
        shoppingCart.getShoppingItems().add(new ShoppingItem(productBall, 2L));

        return shoppingCart;
    }

}