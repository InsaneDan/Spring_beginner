package com.geekbrains.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contacts")
public class ContactsController {

    @ModelAttribute("activePage")
    String activePage() {
        return "contacts";
    }

    @GetMapping
    public String contacts (Model model) {
        return "contacts";
    }

}
