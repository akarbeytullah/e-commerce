package com.trendyol.ecommerce.shopping.web.shopping;

import com.trendyol.ecommerce.shopping.web.NullValueExistInBodyException;
import com.trendyol.ecommerce.shopping.web.ValueNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author bakar
 * @since 7.07.2019 12:59
 */
@ControllerAdvice(basePackageClasses = ShoppingCartController.class)
public class ShoppingCartRestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NullValueExistInBodyException.class, ValueNotFoundException.class, CouponIsNotApplicable.class})
    public ResponseEntity handleNullValueExist(Exception exception) {

        return new ResponseEntity(exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
