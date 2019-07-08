package com.trendyol.ecommerce.shopping.web.coupon;

import com.trendyol.ecommerce.shopping.web.NullValueExistInBodyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author bakar
 * @since 7.07.2019 00:08
 */
@ControllerAdvice(basePackageClasses = CouponController.class)
public class CouponRestResponseEntityExceptionHandler {

    @ExceptionHandler({NullValueExistInBodyException.class})
    public ResponseEntity handleNullValueExist(Exception exception) {

        return new ResponseEntity(exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
