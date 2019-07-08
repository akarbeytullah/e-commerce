package com.trendyol.ecommerce.shopping.web.shopping;

import com.trendyol.ecommerce.shopping.manager.coupon.CouponManagement;
import com.trendyol.ecommerce.shopping.manager.product.ProductManagement;
import com.trendyol.ecommerce.shopping.manager.shopping.ShoppingCartManagement;
import com.trendyol.ecommerce.shopping.model.Product;
import com.trendyol.ecommerce.shopping.model.ShoppingCart;
import com.trendyol.ecommerce.shopping.model.discount.coupon.Coupon;
import com.trendyol.ecommerce.shopping.web.NullValueExistInBodyException;
import com.trendyol.ecommerce.shopping.web.ValueNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bakar
 * @since 6.07.2019 19:08
 */

@RestController
@RequestMapping("cart")
@CrossOrigin
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartManagement shoppingCartManagement;

    private final ProductManagement productManagement;

    private final CouponManagement couponManagement;

    @PostMapping(value = "/addItem/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@PathVariable Long productId, @RequestBody Map<String, String> body) {

        if (isAnyNullValueForCart(body)) {
            throw new NullValueExistInBodyException("Null value is present when adding new Item to Cart!");
        }

        final Product product = getProduct(productId);
        final Long quantity = Long.valueOf(body.get("quantity"));
        shoppingCartManagement.addItem(product, quantity, body.get("user"));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/getDeliveryCost", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getDeliveryCost(@RequestParam(value = "customerName") String customerName) {

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("result", shoppingCartManagement.getDeliveryCost(getShoppingCart(customerName)).toString());
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @GetMapping(value = "/getCampaignDiscount", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCampaignDiscount(@RequestParam(value = "customerName") String customerName) {

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("result", shoppingCartManagement.getCampaignDiscount(getShoppingCart(customerName)).toString());
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @GetMapping(value = "/getCouponDiscount", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCouponDiscount(@RequestParam(value = "customerName") String customerName) {

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("result", shoppingCartManagement.getCouponDiscount(getShoppingCart(customerName)).toString());
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @GetMapping(value = "/getTotalAmountAfterDiscounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getTotalAmountAfterDiscounts(@RequestParam(value = "customerName") String customerName) {

        Map<String, String> responseMap = new HashMap<>();

        final ShoppingCart shoppingCart = getShoppingCart(customerName);
        final Double discount = shoppingCartManagement.totalAmountWithDiscount(shoppingCart);

        responseMap.put("result", discount.toString());
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @PostMapping(value = "/applyCoupon/{couponId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity applyCoupon(@PathVariable Long couponId, @RequestParam(value = "customerName") String customerName) {

        final ShoppingCart shoppingCart = getShoppingCart(customerName);
        final Coupon coupon = getCoupon(couponId);

        if (!shoppingCartManagement.isCouponApplicable(shoppingCart, coupon)) {
            throw new CouponIsNotApplicable("Coupon min value should be greater than cart total amount!");
        }

        shoppingCartManagement.addCouponToCart(shoppingCart, coupon);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Coupon getCoupon(Long couponId) {
        return couponManagement.getCoupon(couponId).orElseThrow(() -> new ValueNotFoundException("Coupon not found!"));
    }

    private ShoppingCart getShoppingCart(String customerName) {

        return shoppingCartManagement.getShoppingCartWithCustomerName(customerName)
                .orElseThrow(() -> new ValueNotFoundException("Shopping cart not found!"));
    }

    private boolean isAnyNullValueForCart(Map<String, String> itemInfo) {
        return itemInfo.get("quantity") == null || itemInfo.get("user") == null;
    }

    private Product getProduct(Long productId) {

        return productManagement.getProduct(productId)
                .orElseThrow(() -> new ValueNotFoundException("Product not found for adding to cart!"));
    }
}
