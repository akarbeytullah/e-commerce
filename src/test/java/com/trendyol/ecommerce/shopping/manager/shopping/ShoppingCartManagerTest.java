package com.trendyol.ecommerce.shopping.manager.shopping;

import com.trendyol.ecommerce.shopping.manager.delivery.DeliveryManagement;
import com.trendyol.ecommerce.shopping.manager.discount.DiscountManagement;
import com.trendyol.ecommerce.shopping.model.Category;
import com.trendyol.ecommerce.shopping.model.Product;
import com.trendyol.ecommerce.shopping.model.ShoppingCart;
import com.trendyol.ecommerce.shopping.model.ShoppingItem;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author bakar
 * @since 7.07.2019 11:17
 */
class ShoppingCartManagerTest {

    private static final long PRODUCT_ID = 1L;

    @Test
    void when_AddItemForCart_Expect_RepositorySaveShouldBeCalledWithProperInfo() {

        final ShoppingCartRepository repository = mock(ShoppingCartRepository.class);

        final ShoppingCartManager shoppingManager = new ShoppingCartManager(repository, null, null);

        final String customerName = "QUEST";

        final ShoppingCart cartFromDb = new ShoppingCart();
        final int itemSizeBeforeSaved = cartFromDb.getShoppingItems().size();
        when(repository.findByCustomerName(customerName)).thenReturn(Optional.of(cartFromDb));

        final Product product = createProduct();
        final Long quantity = 20L;
        shoppingManager.addItem(product, quantity, customerName);

        ArgumentCaptor<ShoppingCart> cartCaptor = ArgumentCaptor.forClass(ShoppingCart.class);
        verify(repository).save(cartCaptor.capture());
        final ShoppingCart shoppingCartToBeSaved = cartCaptor.getValue();

        assertEquals(itemSizeBeforeSaved + 1, shoppingCartToBeSaved.getShoppingItems().size(),
                "shopping item size should increment by one!");

        final ShoppingItem addedItem =
                shoppingCartToBeSaved.getShoppingItems().get(shoppingCartToBeSaved.getShoppingItems().size() - 1);

        assertEquals(product, addedItem.getProduct(), "Added product should match!");
        assertEquals(quantity, addedItem.getQuantity(), "Added quantity should match!");
    }

    @Test
    void when_AddItemForCartExistOfProduct_Expect_RepositorySaveShouldBeCalledWithNewIncrementedQuantity() {

        final ShoppingCartRepository repository = mock(ShoppingCartRepository.class);
        final ShoppingCartManager shoppingManager = new ShoppingCartManager(repository, null, null);

        final String customerName = "QUEST";

        final ShoppingCart cartFromDb = new ShoppingCart();
        final Long incrementCountBeforeAdding = 10L;
        cartFromDb.getShoppingItems().add(new ShoppingItem(createProduct(), incrementCountBeforeAdding));

        when(repository.findByCustomerName(customerName)).thenReturn(Optional.of(cartFromDb));

        final Product product = createProduct();
        final Long quantity = 20L;
        shoppingManager.addItem(product, quantity, customerName);

        ArgumentCaptor<ShoppingCart> cartCaptor = ArgumentCaptor.forClass(ShoppingCart.class);
        verify(repository).save(cartCaptor.capture());
        final ShoppingCart shoppingCartToBeSaved = cartCaptor.getValue();

        final Long finalCount = incrementCountBeforeAdding + quantity;
        assertEquals(finalCount, shoppingCartToBeSaved.getShoppingItems().get(0).getQuantity(),
                "shopping item quantity should increment by added product count!");
    }

    @Test
    void when_getDiscountCalls_Expect_DiscountManagementShouldBeCalled() {

        final DiscountManagement discountManagement = mock(DiscountManagement.class);
        final ShoppingCartManager shoppingManager = new ShoppingCartManager(null, discountManagement, null);

        when(discountManagement.calculate(Mockito.any())).thenReturn(3.0);

        final ShoppingCart shoppingCart = new ShoppingCart();
        shoppingManager.getCampaignDiscount(shoppingCart);

        verify(discountManagement).calculate(shoppingCart);
    }

    @Test
    void when_getDeliveryCostCalls_Expect_DeliveryManagementShouldBeCalled() {

        final DeliveryManagement deliveryManagement = mock(DeliveryManagement.class);
        final ShoppingCartManager shoppingManager =
                new ShoppingCartManager(null, null, deliveryManagement);

        when(deliveryManagement.getDeliveryCost(Mockito.any())).thenReturn(5.29);

        final ShoppingCart shoppingCart = new ShoppingCart();
        shoppingManager.getDeliveryCost(shoppingCart);

        verify(deliveryManagement).getDeliveryCost(shoppingCart);
    }

    @Test
    void when_totalAmountWithDiscount_Expect_ReturnProperResult() {

        final DiscountManagement discountManagement = mock(DiscountManagement.class);
        final ShoppingCartManager shoppingManager = new ShoppingCartManager(null, discountManagement, null);

        final double discountPrice = 3.0;
        when(discountManagement.calculate(Mockito.any())).thenReturn(discountPrice);

        final ShoppingCart shoppingCart = mock(ShoppingCart.class);
        final double cartPriceWithoutDiscount = 300.0;
        when(shoppingCart.calculatePrice()).thenReturn(cartPriceWithoutDiscount);

        final Double calculatedWithTest = cartPriceWithoutDiscount - discountPrice;
        assertEquals(calculatedWithTest, shoppingManager.totalAmountWithDiscount(shoppingCart), "Two calculated values are should matched!");
        verify(discountManagement).calculate(shoppingCart);
    }

    private Product createProduct() {

        final Product product = new Product("Apple", "100", new Category("Food"));
        product.setId(PRODUCT_ID);
        return product;
    }

}