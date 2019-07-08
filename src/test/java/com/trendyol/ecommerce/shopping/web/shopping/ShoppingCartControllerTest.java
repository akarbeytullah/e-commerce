package com.trendyol.ecommerce.shopping.web.shopping;

import com.trendyol.ecommerce.shopping.manager.coupon.CouponManagement;
import com.trendyol.ecommerce.shopping.manager.product.ProductManagement;
import com.trendyol.ecommerce.shopping.manager.shopping.ShoppingCartManagement;
import com.trendyol.ecommerce.shopping.model.Category;
import com.trendyol.ecommerce.shopping.model.Product;
import com.trendyol.ecommerce.shopping.model.ShoppingCart;
import com.trendyol.ecommerce.shopping.model.discount.coupon.Coupon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author bakar
 * @since 7.07.2019 12:55
 */

@ExtendWith(MockitoExtension.class)
class ShoppingCartControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ShoppingCartManagement shoppingCartManagement;

    @Mock
    private ProductManagement productManagement;

    @Mock
    private CouponManagement couponManagement;

    @BeforeEach
    void setUp() {

        ShoppingCartController controller = new ShoppingCartController(shoppingCartManagement, productManagement, couponManagement);
        mockMvc = MockMvcBuilders.
                standaloneSetup(controller)
                .setControllerAdvice(ShoppingCartRestResponseEntityExceptionHandler.class)
                .build();
    }

    @Test
    void when_ExceptionThrowsWithNullQuantityValue_Expect_ControllerAdviceShouldReturnProperResponse() throws Exception {

        String requestJson = "{\"user\":\"QUEST\"}";

        mockMvc.perform(post("/cart/addItem/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void when_AddItemCalls_Expect_ShoppingCartManagementShouldSaveProduct() throws Exception {

        final String quantity = "10";
        final String user = "QUEST";
        String requestJson = "{\"quantity\":\"" + quantity + "\", \"user\":\"" + user + "\"}";
        final String productId = "1";

        final Product product = new Product("Apple", "100", new Category("Food"));
        when(productManagement.getProduct(Long.valueOf(productId))).thenReturn(Optional.of(product));

        mockMvc.perform(post("/cart/addItem/" + productId)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().is2xxSuccessful());

        verify(shoppingCartManagement).addItem(product, Long.valueOf(quantity), user);
    }

    @Test
    void when_getDeliveryCostCalls_Expect_ReturnCalculatedValue() throws Exception {

        String customerName = "customerName";
        String customerNameValue = "QUEST";

        String expectedResult = "22.99";

        final ShoppingCart shoppingCart = new ShoppingCart();
        when(shoppingCartManagement.getShoppingCartWithCustomerName(customerNameValue)).thenReturn(Optional.of(shoppingCart));
        when(shoppingCartManagement.getDeliveryCost(shoppingCart)).thenReturn(Double.valueOf(expectedResult));

        mockMvc.perform(get("/cart/getDeliveryCost")
                .param(customerName, customerNameValue))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", equalTo(expectedResult)));
    }

    @Test
    void when_getCampaignDiscountCalls_Expect_ReturnCalculatedDiscount() throws Exception {

        String customerName = "customerName";
        String customerNameValue = "QUEST";

        String expectedResult = "20.0";

        final ShoppingCart shoppingCart = new ShoppingCart();
        when(shoppingCartManagement.getShoppingCartWithCustomerName(customerNameValue)).thenReturn(Optional.of(shoppingCart));
        when(shoppingCartManagement.getCampaignDiscount(shoppingCart)).thenReturn(Double.valueOf(expectedResult));

        mockMvc.perform(get("/cart/getCampaignDiscount")
                .param(customerName, customerNameValue))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", equalTo(expectedResult)));
    }

    @Test
    void when_getCouponDiscountCalls_Expect_ReturnCalculatedCouponDiscount() throws Exception {

        String customerName = "customerName";
        String customerNameValue = "QUEST";

        String expectedResult = "23.0";

        final ShoppingCart shoppingCart = new ShoppingCart();
        when(shoppingCartManagement.getShoppingCartWithCustomerName(customerNameValue)).thenReturn(Optional.of(shoppingCart));
        when(shoppingCartManagement.getCouponDiscount(shoppingCart)).thenReturn(Double.valueOf(expectedResult));

        mockMvc.perform(get("/cart/getCouponDiscount")
                .param(customerName, customerNameValue))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", equalTo(expectedResult)));
    }

    @Test
    void when_getTotalAmount_Expect_ReturnCalculatedAmountAfterDiscount() throws Exception {

        String customerName = "customerName";
        String customerNameValue = "QUEST";

        String expectedResult = "11.5";

        final ShoppingCart shoppingCart = new ShoppingCart();
        when(shoppingCartManagement.getShoppingCartWithCustomerName(customerNameValue)).thenReturn(Optional.of(shoppingCart));
        when(shoppingCartManagement.totalAmountWithDiscount(shoppingCart)).thenReturn(Double.valueOf(expectedResult));

        mockMvc.perform(get("/cart/getTotalAmountAfterDiscounts")
                .param(customerName, customerNameValue))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", equalTo(expectedResult)));
    }

    @Test
    void when_applyCouponCallsWithSuccess_Expect_returnSuccessResponse() throws Exception {

        String customerName = "customerName";
        String customerNameValue = "QUEST";

        Long couponId = 1L;

        final ShoppingCart shoppingCart = new ShoppingCart();
        when(shoppingCartManagement.getShoppingCartWithCustomerName(customerNameValue)).thenReturn(Optional.of(shoppingCart));

        final Coupon coupon = new Coupon();
        when(couponManagement.getCoupon(1L)).thenReturn(Optional.of(coupon));

        when(shoppingCartManagement.isCouponApplicable(shoppingCart, coupon)).thenReturn(true);

        mockMvc.perform(post("/cart/applyCoupon/" + couponId.toString())
                .param(customerName, customerNameValue)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void when_applyCouponCallsWithFailure_Expect_returnSuccessResponse() throws Exception {

        String customerName = "customerName";
        String customerNameValue = "QUEST";

        Long couponId = 1L;

        final ShoppingCart shoppingCart = new ShoppingCart();
        when(shoppingCartManagement.getShoppingCartWithCustomerName(customerNameValue)).thenReturn(Optional.of(shoppingCart));

        final Coupon coupon = new Coupon();
        when(couponManagement.getCoupon(1L)).thenReturn(Optional.of(coupon));

        when(shoppingCartManagement.isCouponApplicable(shoppingCart, coupon)).thenReturn(false);

        mockMvc.perform(post("/cart/applyCoupon/" + couponId.toString())
                .param(customerName, customerNameValue)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }
}