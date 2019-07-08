package com.trendyol.ecommerce.shopping.web.category;

import com.trendyol.ecommerce.shopping.web.DataAlreadyDefinedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author bakar
 * @since 6.07.2019 22:12
 */
@ControllerAdvice(basePackageClasses = CategoryController.class)
public class CategoryRestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({DataAlreadyDefinedException.class})
    public ResponseEntity handleAlreadyExistData(Exception exception) {

        return new ResponseEntity(exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
