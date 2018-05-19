package com.example.nordicmotorhomes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MechanicController {
    private static final String defaultPath = "/service";
    private static final String defaultFilePath = "mechanic/";

    @GetMapping(defaultPath)
    public String index(){
        return defaultFilePath + "index";
    }

    @GetMapping(defaultPath + "/{id}")
    public String serviceDetails(@PathVariable("id")int id, Model model){

        return defaultFilePath + "details";
    }

    @GetMapping(defaultPath + "/check/{motorhouseID}")
    public String checkMotorhouse(@PathVariable("motorhouseID")int id, Model model){

        return defaultFilePath + "check";
    }

    @PostMapping(defaultPath + "/check/{motorhouseID}")
    public String checkMotorhouse(@PathVariable("motorhouseID")int id,
                                  @RequestParam("some")String check){
        return "redirect:" + defaultPath;
    }

}
