package com.spr.exceptions;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
