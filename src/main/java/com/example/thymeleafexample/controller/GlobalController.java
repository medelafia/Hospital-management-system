package com.example.thymeleafexample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GlobalController {
    @GetMapping("/index")
    public String index() {
        return "index" ;
    }

    @GetMapping("/login")
    public String login(Model model ,@RequestParam(required = false) String  error ) {
        if(error != null) model.addAttribute("error", error);
        return "login" ;
    }
}
