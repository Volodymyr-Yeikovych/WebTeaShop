package com.spr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.executable.ValidateOnExecution;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Client {

    private final String EMAIL_VALIDATION_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
            "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
            "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?" +
            "[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-" +
            "\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    private UUID id;

    private String name;

    @NotBlank(message = "Password must not be blank")
    @NotNull(message = "Password must not be blank")
    private String password;

    @NotBlank
    @NotNull
    @Pattern(regexp = EMAIL_VALIDATION_REGEX, message = "Email is incorrect")
    private String email;

    private boolean isAdmin;

    private boolean hasErrors;

    public Client(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.password = client.getPassword();
        this.email = client.getEmail();
        this.isAdmin = client.isAdmin();
    }

    public Client(UUID id, String name, String password, String email, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.isAdmin = isAdmin;
    }
}
