package com.trendyol.ecommerce.shopping.manager.coupon;

import com.trendyol.ecommerce.shopping.model.discount.coupon.Coupon;
import org.springframework.data.repository.CrudRepository;

/**
 * @author bakar
 * @since 6.07.2019 23:55
 */
public interface CouponRepository extends CrudRepository<Coupon, Long> {
}
