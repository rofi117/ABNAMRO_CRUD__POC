package com.example.springdb.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class AccountNumberNotValidException extends RuntimeException {
    public AccountNumberNotValidException(String errorMessage) {
        super(errorMessage);
    }
}




