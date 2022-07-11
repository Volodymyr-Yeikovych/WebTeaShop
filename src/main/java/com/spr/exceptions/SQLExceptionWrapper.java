package com.spr.exceptions;

public class SQLExceptionWrapper extends RuntimeException {
    public SQLExceptionWrapper(String message) {
        super(message);
    }
}
