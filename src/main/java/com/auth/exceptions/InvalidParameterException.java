package com.auth.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class InvalidParameterException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = -5455843470630454045L;

    public InvalidParameterException(String message) {
        super(message);
    }
}
