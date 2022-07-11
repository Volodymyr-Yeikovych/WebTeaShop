package com.spr.controller;

import com.spr.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/")
public class LogInController {

    private final ClientService clientService;

    @Autowired
    public LogInController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String displayHomePage() {
        return "homepage";
    }
}
