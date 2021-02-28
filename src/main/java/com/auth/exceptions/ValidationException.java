package com.auth.exceptions;

public class ValidationException extends RuntimeException  {
    public ValidationException(String exception) {
        super(exception);
    }
}
