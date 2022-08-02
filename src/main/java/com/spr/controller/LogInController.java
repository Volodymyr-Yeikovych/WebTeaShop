package com.spr.controller;

import com.spr.exceptions.InvalidPasswordException;
import com.spr.exceptions.NoSuchEmailException;
import com.spr.model.Client;
import com.spr.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("/login")
@Slf4j
public class LogInController {

    private final ClientService clientService;

    @Autowired
    public LogInController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String displayHomePage() {
        log.info("Inside Method: displayLoginPage()");
        return "loginPage";
    }

    @PostMapping
    public String validateClient(@Valid @ModelAttribute Client client, Errors errors, SessionStatus sessionStatus) {
        log.info("Inside Method: validateClient()");
        String email = client.getEmail();
        String password = client.getPassword();

        if (!validateClient(email, password)) {
            client.setHasErrors(true);
            return "loginPage";
        }

        client = clientService.loginClientOrThrow(email, password);

        if (errors.hasErrors() || !clientService.clientExists(client)) {
            log.info("Client was not authorised: {} with such errors: invalid ? {}, doesn't exists ? {}", client,
                    errors.hasErrors(), !clientService.clientExists(client));
            client.setHasErrors(true);
            return "loginPage";
        }
        clientService.setLoggedIn(true);
        client.setHasErrors(false);
        log.info("Client authorised: {}", client);
        sessionStatus.setComplete();

        return "redirect:/homepage";
    }

    private boolean validateClient(String email, String password) {
        try {
            new Client(clientService.loginClientOrThrow(email, password));
        } catch (NoSuchEmailException | InvalidPasswordException e) {
            log.info("Client entered invalid email or password: {}, {}", email, password);
            return false;
        }
        return true;
    }

    @ModelAttribute(name = "client")
    public Client client() {
        return new Client();
    }

}
