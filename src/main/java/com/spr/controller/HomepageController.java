package com.spr.controller;

import com.spr.exceptions.NotEnoughTeaInStockException;
import com.spr.model.Tea;
import com.spr.model.TeaOrder;
import com.spr.service.ClientService;
import com.spr.service.TeaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/homepage")
@SessionAttributes("teaOrder")
public class HomepageController {

    //TODO: fix that you can log in without actually logging in, just by typing /homepage in the path
    private final ClientService clientService;
    private final TeaService teaService;

    @Autowired
    public HomepageController(ClientService clientService, TeaService teaService) {
        this.clientService = clientService;
        this.teaService = teaService;
    }


    @GetMapping
    public String displayHomepage(Model model) {
        log.info("Displaying homepage.");
        model.addAttribute("order", teaService.getAllTea());
        return "homepage";
    }

    @ModelAttribute(name = "tea")
    public Tea tea(){
        return new Tea();
    }


    @ModelAttribute(name = "teaOrder")
    public TeaOrder teaOrder() {
        return new TeaOrder();
    }

    @PostMapping
    public String processOrder(@Valid Tea tea, Errors errors, @ModelAttribute TeaOrder teaOrder, Model model) {
        log.info("Inside processOrder()");

        if (errors.hasErrors()) {
            log.info("Errors has errors: {}", errors.getAllErrors());
            model.addAttribute("order", teaService.getAllTea());
            return "homepage";
        }

        try {
            tea = teaService.initTeaOrThrow(tea);
        } catch (NotEnoughTeaInStockException e) {
            log.info("Client ordered to much tea.");
            tea.setHasErrors(true);
            model.addAttribute("order", teaService.getAllTea());
            return "homepage";
        }

        tea.setHasErrors(false);
        log.info("Adding tea: {}", tea);
        teaService.normalize(tea, teaOrder);
        if (!tea.isNormalized()) {
            teaOrder.addTeaToOrder(tea);
        }
        log.info("Tea added into order: {}", teaOrder);
        return "redirect:/purchase";
    }
}
