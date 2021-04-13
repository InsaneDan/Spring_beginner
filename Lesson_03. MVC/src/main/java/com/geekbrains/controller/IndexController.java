package com.geekbrains.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping(value="/")
    public String index (Model model) {
        model.addAttribute("message", "Hello World");
        return "index";
    }

}
