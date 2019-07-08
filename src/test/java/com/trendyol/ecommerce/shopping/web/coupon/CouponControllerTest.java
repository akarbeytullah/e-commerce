package com.trendyol.ecommerce.shopping.web.coupon;

import com.trendyol.ecommerce.shopping.manager.coupon.CouponManagement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author bakar
 * @since 7.07.2019 00:18
 */
@ExtendWith(MockitoExtension.class)
class CouponControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CouponManagement couponManagement;

    @BeforeEach
    void setUp() {

        CouponController controller = new CouponController(couponManagement);
        mockMvc = MockMvcBuilders.
                standaloneSetup(controller)
                .setControllerAdvice(CouponRestResponseEntityExceptionHandler.class)
                .build();
    }

    @Test
    void when_ExceptionThrowsWithNullAmountValue_Expect_ControllerAdviceShouldReturnProperResponse() throws Exception {

        String requestJson = "{\"discount\":\"10\"}";

        mockMvc.perform(post("/coupon/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void when_AddCouponCalls_Expect_ProductManagementShouldSaveCoupon() throws Exception {

        final String amount = "100";
        final String discount = "10";
        String requestJson = "{\"amount\":\"" + amount + "\", \"discount\":\"" + discount + "\"}";

        mockMvc.perform(post("/coupon/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().is2xxSuccessful());

        verify(couponManagement).addCoupon(Long.valueOf(amount), Long.valueOf(discount));
    }
}