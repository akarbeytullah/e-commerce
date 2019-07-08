package com.trendyol.ecommerce.shopping.manager.coupon;

import com.trendyol.ecommerce.shopping.model.discount.coupon.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author bakar
 * @since 6.07.2019 23:46
 */
@Service
@RequiredArgsConstructor
public class CouponManager implements CouponManagement {

    private final CouponRepository couponRepository;

    @Override
    public void addCoupon(Long amount, Long discount) {
        couponRepository.save(new Coupon(amount, discount));
    }

    @Override
    public Optional<Coupon> getCoupon(Long id) {
        return couponRepository.findById(id);
    }
}
