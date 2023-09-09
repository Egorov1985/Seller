package com.example.buysell.exception.userException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserException> handleUserNotFoundException(UserNotFoundException userNotFoundException){
        UserException userException = new UserException(userNotFoundException.getMessage(),
                userNotFoundException.getCause(),
                HttpStatus.NOT_FOUND);
        return new ResponseEntity<>( userException,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserActiveBannedException.class)
    public ResponseEntity<UserException> handleUserActiveBannedException(UserActiveBannedException userActiveBanned){
        UserException userException = new UserException(userActiveBanned.getMessage(),
                userActiveBanned.getCause(),
                HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>( userException,
                HttpStatus.BAD_REQUEST);
    }
}
