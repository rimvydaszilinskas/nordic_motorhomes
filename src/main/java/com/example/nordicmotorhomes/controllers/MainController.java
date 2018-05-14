package com.example.nordicmotorhomes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping({"/", "/index", "/home"})
    public String index(){
        return "index";
    }

//    @GetMapping("/error")
//    public String error(){
//        return "";
//    }
}
