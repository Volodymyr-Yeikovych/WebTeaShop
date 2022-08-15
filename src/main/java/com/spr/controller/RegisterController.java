package com.spr.controller;

import com.spr.exceptions.NoSuchEmailException;
import com.spr.model.Client;
import com.spr.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/register")
public class RegisterController {

    private final ClientService clientService;

    @Autowired
    public RegisterController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String displayRegisterPage () {
        return "registerPage";
    }

    @PostMapping
    public String processRegistration (@Valid @ModelAttribute Client client, Errors errors) {
        log.info("Inside processRegistration()");
        if (errors.hasErrors()) return "registerPage";

        try {
            clientService.getClientByEmail(client.getEmail());
            client.setHasErrors(true);
            return "registerPage";
        } catch (NoSuchEmailException e) {
            client.setId(UUID.randomUUID());
            log.info("Registering client with id: {}, name: {}, password: {}, email: {}, isAdmin: {}", client.getId(),
                    client.getName(), client.getPassword(), client.getEmail(), client.isAdmin());
            client.setPassword(clientService.encryptPwAndReturn(client.getPassword()));
            clientService.addClient(new Client(client));
        }
        client.setHasErrors(false);
        clientService.setLoggedIn(true);
        return "redirect:/homepage";
    }

    @ModelAttribute
    public Client client () {
        return new Client();
    }
}
