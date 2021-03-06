package com.example.nordicmotorhomes.controllers;

import com.example.nordicmotorhomes.models.MotorHouse;
import com.example.nordicmotorhomes.models.Transmission;
import com.example.nordicmotorhomes.repositories.IMotorHouse;
import com.example.nordicmotorhomes.repositories.IReservation;
import com.example.nordicmotorhomes.repositories.MotorHouseRepository;
import com.example.nordicmotorhomes.repositories.ReservationRepository;
import com.example.nordicmotorhomes.utilities.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@Controller
public class MotorHouseController {

    private IMotorHouse motorHouseRepository = new MotorHouseRepository();
    private IReservation reservationRepository = new ReservationRepository();

    private static final String defaultPath = "/motorhouse";
    private static final String defaultFilePath = "motorhouses/";

    @GetMapping(defaultPath)
    public String index(Model model){
        List<MotorHouse> motorHouses = motorHouseRepository.getAll();

        //to avoid conflicts with thymeleaf conditionals
        if(motorHouses == null)
            motorHouses = new LinkedList<>();

        model.addAttribute("motorhouses", motorHouses);
        model.addAttribute("sBrand", "default");
        model.addAttribute("sGearbox", "default");
        model.addAttribute("sTransmission", new Transmission("default"));
        model.addAttribute("transmissions", motorHouseRepository.getAllTransmissions());
        model.addAttribute("gearboxes", motorHouseRepository.getAllGearboxes());
        model.addAttribute("manufacturers", motorHouseRepository.getAllManufacturers());

        return defaultFilePath + "display";
    }

    @GetMapping(defaultPath + "/delete/{id}")
    public String delete(@PathVariable("id") int id){
        motorHouseRepository.delete(id);
        return "redirect:" + defaultPath;
    }

    @GetMapping(defaultPath + "/addNew")
    public String addNew(){
        return defaultFilePath + "new";
    }

    @PostMapping(defaultPath + "/details")
    @ResponseBody
    public String JSONDetails(@RequestParam("id") int id){
        JSON json = new JSON();
        MotorHouse motorHouse = motorHouseRepository.get(id);
        json.add("manufacturer", motorHouse.getManufacturer())
                .add("model", motorHouse.getModel())
                .add("bed_count", motorHouse.getBed_count())
                .add("seats", motorHouse.getSeats())
                .add("weight", motorHouse.getWeight() + "kg")
                .add("description", "  ")
                .add("gearbox", motorHouse.getGearbox())
                .add("power", motorHouse.getPower())
                .add("year", motorHouse.getYear())
                .add("mileage", motorHouse.getMileage())
                .add("transmission", motorHouse.getTransmission());

        return json.getJSON();
    }

    @PostMapping(defaultPath + "/delete/details")
    @ResponseBody
    public String deleteDetails(@RequestParam("id") int id){
        //used to display a modal before deleting motorhouse
        JSON json = new JSON();
        MotorHouse motorHouse = motorHouseRepository.get(id);
        json.add("model", motorHouse.getManufacturer() + " " + motorHouse.getModel());
        json.add("id", motorHouse.getId());

        return json.getJSON();
    }

    @PostMapping(defaultPath + "/create")
    public String create(@ModelAttribute MotorHouse motorHouse){
        motorHouseRepository.add(motorHouse);
        return "redirect:" + defaultPath;
    }

    @GetMapping(defaultPath + "/filter")
    public String filtered(Model model,
                           @RequestParam("brands") String brand,
                           @RequestParam("gearbox") String gearbox,
                           @RequestParam("transmission") String transmission,
    @RequestParam("price") String price){

        if(brand.equals("default") && gearbox.equals("default") && transmission.equals("default") && price.length() == 0)
            return "redirect:" + defaultPath;

        if(price.length() == 0)
            price = "0";

        List<MotorHouse> motorHouses = motorHouseRepository.getAllOnFilters(brand, gearbox, transmission, Integer.parseInt(price));

        if(motorHouses == null)
            motorHouses = new LinkedList<>();

        model.addAttribute("motorhouses", motorHouses);
        model.addAttribute("sBrand", brand);
        model.addAttribute("sGearbox", gearbox);
        model.addAttribute("sTransmission", new Transmission(transmission));
        model.addAttribute("transmissions", motorHouseRepository.getAllTransmissions());
        model.addAttribute("gearboxes", motorHouseRepository.getAllGearboxes());
        model.addAttribute("manufacturers", motorHouseRepository.getAllManufacturers());

        return defaultFilePath + "display";
    }

    @GetMapping(defaultPath + "/edit/{id}")
    public String edit(@PathVariable("id")int id, Model model){

        model.addAttribute("motorhouse", motorHouseRepository.get(id));

        return defaultFilePath + "edit";
    }

    @PostMapping(defaultPath + "/edit")
    public String edit(@ModelAttribute MotorHouse motorHouse){
        motorHouseRepository.update(motorHouse);
        return "redirect:" + defaultPath;
    }

    @GetMapping(defaultPath + "/details/{id}")
    public String details(@PathVariable("id")int id, Model model){

        model.addAttribute("motorhouse", motorHouseRepository.get(id));
        model.addAttribute("reservations", reservationRepository.getVehicleReservations(id));

        return defaultFilePath + "details";
    }

    @PostMapping(defaultPath + "/fullDetails")
    @ResponseBody
    public String fullDetails(@RequestParam("id")int id){
        JSON json = new JSON();

        return json.getJSON();
    }
}
