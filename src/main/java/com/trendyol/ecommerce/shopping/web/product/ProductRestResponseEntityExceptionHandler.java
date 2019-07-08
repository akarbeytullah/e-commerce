package com.trendyol.ecommerce.shopping.web.product;

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
 * @since 6.07.2019 19:30
 */

@ControllerAdvice(basePackageClasses = ProductController.class)
public class ProductRestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NullValueExistInBodyException.class, ValueNotFoundException.class})
    public ResponseEntity handleNullValueExist(Exception exception) {

        return new ResponseEntity(exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
