package com.spr.controller;

import com.spr.model.Client;
import com.spr.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
@Slf4j
public class LogInController {

    private final ClientService clientService;

    @Autowired
    public LogInController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String displayHomePage() {
        return "loginPage";
    }

    @PostMapping
    public String validateClient(@Valid @ModelAttribute Client client, Errors errors) {
        if (errors.hasErrors() || !clientService.clientExists(client)) return "loginPage";
        log.info("Client authorised: {}", client);

        return "redirect:/homepage";
    }

    @ModelAttribute(name = "client")
    public Client client(@ModelAttribute(name = "email") String email,
                         @ModelAttribute(name = "password") String password) {
        return clientService.loginClientOrThrow(email, password);
    }
}
