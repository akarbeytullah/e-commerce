package com.trendyol.ecommerce.shopping.manager.coupon;

import com.trendyol.ecommerce.shopping.model.discount.coupon.Coupon;

import java.util.Optional;

/**
 * @author bakar
 * @since 6.07.2019 23:45
 */
public interface CouponManagement {

    void addCoupon(Long amount, Long discount);

    Optional<Coupon> getCoupon(Long id);
}
