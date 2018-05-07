package com.example.nordicmotorhomes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class ReservationController {
    //all paths should start with /reservation
    private static final String defaultPath = "/reservation";
    private static final String defaultFilePath = "reservations/";

    @GetMapping(defaultPath)
    public String index(){
        return defaultFilePath + "search";

    }

    @PostMapping(defaultPath + "/reserve")
    public String search(@RequestParam("dateFrom")String dateFrom,
                         @RequestParam("dateTo")String dateTo,
                         @RequestParam("seats")String seats,
                         @RequestParam("beds")String beds){
        System.out.println(dateFrom);
        return defaultFilePath + "search";
    }
}
