package com.example.loans.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LoanAlreadyExistException extends RuntimeException {
    public LoanAlreadyExistException(String message) {
        super(message);
    }
}
