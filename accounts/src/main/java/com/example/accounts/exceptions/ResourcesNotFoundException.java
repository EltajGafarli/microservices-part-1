package com.example.accounts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourcesNotFoundException extends RuntimeException{
    public ResourcesNotFoundException(String message, String fieldName, String fieldValue) {
        super(String.format("%s not found with the given input data %s: %s", message, fieldName, fieldValue));
    }
}
