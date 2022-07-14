package com.spr.exceptions;

public class NoSuchEmailException extends RuntimeException {
    public NoSuchEmailException(String message) {
        super(message);
    }
}
