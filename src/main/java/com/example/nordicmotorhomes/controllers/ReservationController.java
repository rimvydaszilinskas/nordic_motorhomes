package com.example.nordicmotorhomes.controllers;

import com.example.nordicmotorhomes.models.MotorHouse;
import com.example.nordicmotorhomes.models.Reservation;
import com.example.nordicmotorhomes.repositories.CustomerRepository;
import com.example.nordicmotorhomes.repositories.MotorHouseRepo;
import com.example.nordicmotorhomes.repositories.ReservationRepository;
import com.example.nordicmotorhomes.utilities.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class ReservationController {
    //all paths should start with /reservation
    private static final String defaultPath = "/reservation";
    private static final String defaultFilePath = "reservations/";

    private ReservationRepository reservationRepository = new ReservationRepository();
    private MotorHouseRepo motorHouseRepo = new MotorHouseRepo();
    private CustomerRepository customerRepository = new CustomerRepository();

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
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);

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

    @GetMapping(defaultPath + "/reserve/{motorhouseID}/{dateFrom}/{dateTo}")
    public String reserve(@PathVariable("motorhouseID")int id,
                          @PathVariable("dateFrom")String dateFrom,
                          @PathVariable("dateTo")String dateTo,
                          Model model){
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        model.addAttribute("motorhouse", motorHouseRepo.get(id));
        model.addAttribute("customers", customerRepository.getAll());
        return defaultFilePath + "create";
    }

    @PostMapping(defaultPath + "/create")
    public String createReservation(@RequestParam("dateFrom")String dateFrom,
                                    @RequestParam("dateTo")String dateTo,
                                    @RequestParam("motorhouseID")String motorhouseID,
                                    @RequestParam("customer")String customerID,
                                    Model model){

        Reservation reservation = new Reservation(0);
        reservation.setDateFrom(dateFrom);
        reservation.setDateTo(dateTo);
        reservation.setMotorhouseID(Integer.parseInt(motorhouseID));
        reservation.setCustomerID(Integer.parseInt(customerID));
        reservation.setStatus("booked");

        if(reservationRepository.create(reservation))
            model.addAttribute("status", true);
        else
            model.addAttribute("status", false);

        return defaultFilePath + "index";
    }
}
