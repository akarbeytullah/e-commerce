package com.trendyol.ecommerce.shopping.web.coupon;

import com.trendyol.ecommerce.shopping.manager.coupon.CouponManagement;
import com.trendyol.ecommerce.shopping.web.NullValueExistInBodyException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author bakar
 * @since 6.07.2019 19:08
 */


@RestController
@RequestMapping("coupon")
@CrossOrigin
@RequiredArgsConstructor
public class CouponController {

    private final CouponManagement couponManagement;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@RequestBody Map<String, String> body) {

        if (isAnyNullValueForCoupon(body)) {
            throw new NullValueExistInBodyException("Null value is present when adding new Coupon!");
        }

        final Long amount = Long.valueOf(body.get("amount"));
        final Long discount = Long.valueOf(body.get("discount"));
        couponManagement.addCoupon(amount, discount);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean isAnyNullValueForCoupon(Map<String, String> couponInfo) {
        return couponInfo.get("amount") == null || couponInfo.get("discount") == null;
    }
}
