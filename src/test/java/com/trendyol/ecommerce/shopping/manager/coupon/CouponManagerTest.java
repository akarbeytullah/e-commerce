package com.trendyol.ecommerce.shopping.manager.coupon;

import com.trendyol.ecommerce.shopping.model.discount.coupon.Coupon;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author bakar
 * @since 6.07.2019 23:59
 */
class CouponManagerTest {

    @Test
    void when_AddCallsForCoupon_Expect_RepositorySaveShouldBeCalled() {

        final CouponRepository repository = mock(CouponRepository.class);
        final CouponManager couponManager = new CouponManager(repository);

        final Long amount = 100L;
        final Long discount = 10L;
        couponManager.addCoupon(amount, discount);

        ArgumentCaptor<Coupon> couponCaptor = ArgumentCaptor.forClass(Coupon.class);
        verify(repository).save(couponCaptor.capture());
        final Coupon coupon = couponCaptor.getValue();

        assertEquals(amount, coupon.getAmount(), "amount should match!");
        assertEquals(discount, coupon.getDiscount(), "discount should match!");
    }

    @Test
    void when_getCoupon_Expect_RepositoryFindByIdShouldBeCalled() {

        final CouponRepository repository = mock(CouponRepository.class);
        final CouponManager couponManager = new CouponManager(repository);

        final long couponId = 1L;
        couponManager.getCoupon(couponId);

        verify(repository).findById(couponId);
    }
}