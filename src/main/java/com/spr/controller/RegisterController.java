package com.spr.controller;

import com.spr.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/register")
public class RegisterController {

    private final ClientService clientService;

    @Autowired
    public RegisterController(ClientService clientService) {
        this.clientService = clientService;
    }

}
