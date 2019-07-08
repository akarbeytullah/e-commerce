package com.trendyol.ecommerce.shopping.web.shopping;

/**
 * @author bakar
 * @since 8.07.2019 01:32
 */
public class CouponIsNotApplicable extends RuntimeException {

    public CouponIsNotApplicable(String message) {
        super(message);
    }
}
