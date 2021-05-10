package com.geekbrains.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/")
public class IndexController {

    @ModelAttribute("activePage")
    String activePage() {
        return "index";
    }

    @GetMapping(value="/")
    public String index (Model model) {
        model.addAttribute("pageTitle", "Main");
        model.addAttribute("date", new Date());
        return "index";
    }
}
