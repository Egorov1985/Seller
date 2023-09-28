package com.example.buysell.exception.productException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class ProductExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProductException> productNotFoundHandle(ProductNotFoundException productNotFoundException){
        ProductException productException = new ProductException(productNotFoundException.getMessage(),
                productNotFoundException.getCause(),
                HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(productException, HttpStatus.NOT_FOUND);
    }
}
