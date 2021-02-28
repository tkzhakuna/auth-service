package com.auth.exceptions;

public class UnexpectedErrorException extends RuntimeException {
    public UnexpectedErrorException(String exception) {
        super(exception);
    }
}
