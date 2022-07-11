package com.spr.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class Client {

    private final UUID id;

    @NotBlank
    @NotNull
    private final String name;

    @NotBlank
    @NotNull
    private final String password;

    @NotBlank
    @NotNull
    private final String email;
    public Client(UUID id, String name, String password, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
