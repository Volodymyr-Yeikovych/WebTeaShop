package com.spr.controller;

import com.spr.model.TeaOrder;
import com.spr.service.ClientService;
import com.spr.service.TeaOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/purchase")
@SessionAttributes("teaOrder")
public class PurchaseController {

    private final TeaOrderService teaOrderService;
    private final ClientService clientService;

    @Autowired
    public PurchaseController(TeaOrderService teaOrderService, ClientService clientService) {
        this.teaOrderService = teaOrderService;
        this.clientService = clientService;
    }

    @GetMapping
    public String displayPurchasePage() {
        if (!clientService.clientIsLoggedIn()) return "redirect:/login";
        return "purchase";
    }

    @PostMapping
    public String processPurchase(@Valid TeaOrder teaOrder, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) return "purchase";

        log.info("Saving teaOrder {}", teaOrder);
        teaOrderService.save(teaOrder);
        sessionStatus.setComplete();
        clientService.endSession();
        return "farewellPage";
    }
}
