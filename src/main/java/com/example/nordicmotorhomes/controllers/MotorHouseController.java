package com.example.nordicmotorhomes.controllers;

import com.example.nordicmotorhomes.models.MotorHouse;
import com.example.nordicmotorhomes.repositories.MotorHouseRepo;
import com.example.nordicmotorhomes.repositories.util.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MotorHouseController {

    MotorHouseRepo motorHouseRepo = new MotorHouseRepo();

    @GetMapping("/")
    public String index(Model model){
        List<MotorHouse> motorHouses = motorHouseRepo.getAll();
        model.addAttribute("motorhouses", motorHouses);

        return "motorhouses/display";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id){
        motorHouseRepo.delete(id);
        return "redirect:/";
    }

    @GetMapping("/addNew")
    public String addNew(){
        return "motorhouses/new";
    }

    @PostMapping("/motorhouse/details")
    @ResponseBody
    public String index(@RequestParam("id") int id){
        //used to display a modal before deleting motorhouse
        JSON json = new JSON();
        MotorHouse motorHouse = motorHouseRepo.get(id);

        json.add("model", motorHouse.getManufacturer() + " " + motorHouse.getModel());

        return json.getJSON();
    }

}
