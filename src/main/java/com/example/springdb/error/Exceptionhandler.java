package com.example.springdb.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Exceptionhandler {
    @ExceptionHandler(AccountNumberNotValidException.class)
    public ResponseEntity<String> handleAccountNumberNotValidException(AccountNumberNotValidException ex) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ex.getMessage());
    }

    @ExceptionHandler(BeneficiaryNoContentException.class)
    public ResponseEntity<String> handleBeneficiaryNoContentException(BeneficiaryNoContentException ex) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ex.getMessage());
    }

    @ExceptionHandler(BeneficiaryNotFoundException.class)
    public ResponseEntity<String> handleBeneficiaryNotFoundException(BeneficiaryNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}







