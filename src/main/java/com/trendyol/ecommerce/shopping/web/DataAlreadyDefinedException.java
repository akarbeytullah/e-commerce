package com.trendyol.ecommerce.shopping.web;

/**
 * @author bakar
 * @since 6.07.2019 22:11
 */
public class DataAlreadyDefinedException extends RuntimeException {

    public DataAlreadyDefinedException(String message) {
        super(message);
    }
}
