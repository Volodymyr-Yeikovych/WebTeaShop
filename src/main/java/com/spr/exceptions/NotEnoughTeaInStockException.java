package com.spr.exceptions;

public class NotEnoughTeaInStockException extends RuntimeException {
    public NotEnoughTeaInStockException(String message) {
        super(message);
    }

    public NotEnoughTeaInStockException() {
    }
}
