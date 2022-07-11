package com.spr.exceptions;

public class InvalidClientIdException extends RuntimeException {
    public InvalidClientIdException(String message) {
        super(message);
    }
}
