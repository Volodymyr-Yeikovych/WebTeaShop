package com.spr.controller;

import com.spr.model.TeaOrder;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping
    public String displayPurchasePage() {
        return "purchase";
    }

    @PostMapping
    public String processPurchase(@Valid TeaOrder teaOrder, Errors errors, SessionStatus sessionStatus) {
        return "purchase";
    }
}
