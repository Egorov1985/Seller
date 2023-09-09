package com.example.buysell.exception.productException;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Data
public class ProductException {
    private String message;
    private Throwable cause;
    private HttpStatus httpStatus;
    private String timestamp;

    public ProductException (String message, Throwable cause, HttpStatus httpStatus) {
        this.message = message;
        this.cause = cause;
        this.httpStatus = httpStatus;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss"));
    }
}
