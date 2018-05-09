package com.example.nordicmotorhomes.controllers;

import com.example.nordicmotorhomes.models.MotorHouse;
import com.example.nordicmotorhomes.models.Reservation;
import com.example.nordicmotorhomes.repositories.MotorHouseRepo;
import com.example.nordicmotorhomes.repositories.ReservationRepository;
import com.example.nordicmotorhomes.utilities.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ReservationController {
    //all paths should start with /reservation
    private static final String defaultPath = "/reservation";
    private static final String defaultFilePath = "reservations/";

    private ReservationRepository reservationRepository = new ReservationRepository();

    @GetMapping(defaultPath)
    public String index(Model model){
        model.addAttribute("reservations", reservationRepository.getAll());

        return defaultFilePath + "index";
    }

    @PostMapping(defaultPath + "/search")
    public String search(@RequestParam("dateFrom")String dateFrom,
                         @RequestParam("dateTo")String dateTo,
                         @RequestParam("seats")String seats,
                         @RequestParam("beds")String beds,
                         Model model){
        //get free motorhouses
        List<MotorHouse> motorHouses = new MotorHouseRepo().getAllFreeMotorhouses(dateFrom, dateTo, Integer.parseInt(seats), Integer.parseInt(beds));

        model.addAttribute("motorhouses", motorHouses);

        return defaultFilePath + "/results";
    }

    @GetMapping(defaultPath + "/reserve")
    public String reservation(){
        return defaultFilePath + "search";
    }

    @PostMapping(defaultPath + "/delete")
    @ResponseBody
    public String deleteInfo(@RequestParam("id")int id){
        Reservation reservation = reservationRepository.get(id);
        JSON json = new JSON();

        json.add("id", id);
        json.add("dateFrom", reservation.getDateFrom().toString());
        json.add("dateTo", reservation.getDateTo().toString());
        return json.getJSON();
    }

    @GetMapping(defaultPath + "/delete/{id}")
    public  String delete(@PathVariable("id")int id){
        reservationRepository.delete(id);
        return "redirect:" + defaultPath;
    }

    @GetMapping(defaultPath + "/reserve/{motorhouseID}")
    public String reserve(@PathVariable("motorhouseID")int id){

        return "";
    }
}
