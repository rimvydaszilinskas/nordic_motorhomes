package com.example.nordicmotorhomes.controllers;

import com.example.nordicmotorhomes.repositories.IReservation;
import com.example.nordicmotorhomes.repositories.ReservationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    IReservation reservationRepository = new ReservationRepository();

    @GetMapping({"/", "/index", "/home"})
    public String index(Model model){
        model.addAttribute("reservations", reservationRepository.getTodaysReservations());
        model.addAttribute("unchecked", reservationRepository.getUncheckedReservations());
        return "index";
    }

//    @GetMapping("/error")
//    public String error(){
//        return "";
//    }
}
