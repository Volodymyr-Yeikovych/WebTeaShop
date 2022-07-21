package com.spr.error;

public class InternalFatalError extends Error {
    public InternalFatalError(String message) {
        super(message);
    }

    public InternalFatalError() {
    }
}
