package com.example.nordicmotorhomes.controllers;

import com.example.nordicmotorhomes.models.Check;
import com.example.nordicmotorhomes.models.Service;
import com.example.nordicmotorhomes.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Controller
public class MechanicController {
    private static final String defaultPath = "/service";
    private static final String defaultFilePath = "mechanic/";

    private IMotorHouse motorHouseRepository = new MotorHouseRepository();
    private IReservation reservationRepository = new ReservationRepository();
    private ICheck checkRepository = new CheckRepository();
    private IService serviceRepository = new ServiceRepository();

    @GetMapping(defaultPath)
    public String index(Model model){
        model.addAttribute("motorhouses", motorHouseRepository.getAll());
        model.addAttribute("services", serviceRepository.getOngoing());
        return defaultFilePath + "index";
    }

    @GetMapping(defaultPath + "/check")
    public String needsCheck(Model model){
        model.addAttribute("reservations", reservationRepository.getUncheckedReservations());
        return defaultFilePath + "needscheck";
    }

    @GetMapping(defaultPath + "/{id}")
    public String serviceDetails(@PathVariable("id")int id, Model model){
        model.addAttribute("service", serviceRepository.get(id));
        return defaultFilePath + "details";
    }

    @GetMapping(defaultPath + "/check/{reservationID}")
    public String checkMotorhouse(@PathVariable("reservationID")int id, Model model){
        model.addAttribute("reservation", reservationRepository.get(id));
        return defaultFilePath + "check";
    }

    @PostMapping(defaultPath + "/check/{reservationID}")
    public String checkMotorhouse(@PathVariable("reservationID")int id,
                                  @RequestParam(value = "lights", required = false)String lights,
                                  @RequestParam(value = "engine", required = false)String engine,
                                  @RequestParam(value = "chasis", required = false)String chasis,
                                  @RequestParam(value = "interior", required = false)String interior,
                                  @RequestParam(value = "exterior", required = false)String exterior,
                                  @RequestParam(value = "notes", required = false)String notes){
        Check check = new Check();

        check.setDate(LocalDate.now());
        check.setReservationID(id);
        check.setNotes(notes);

        if(lights != null)
            check.setLights(lights.equals("OK"));
        else
            check.setLights(false);
        if(engine != null)
            check.setEngine(engine.equals("OK"));
        else
            check.setEngine(false);
        if(chasis != null)
            check.setChassis(chasis.equals("OK"));
        else
            check.setChassis(false);
        if(interior != null)
            check.setInterior(interior.equals("OK"));
        else
            check.setInterior(false);
        if(exterior != null)
            check.setExterior(exterior.equals("OK"));
        else
            check.setExterior(false);

        checkRepository.create(check);

        return "redirect:" + defaultPath;
    }

    @GetMapping(defaultPath + "/register/{motorhomeID}")
    public String registerService(@PathVariable("motorhomeID")int id, Model model){
        model.addAttribute("motorhome", motorHouseRepository.get(id));

        return defaultFilePath + "register";
    }

    @PostMapping(defaultPath + "/register/{motorhomeID}")
    public String registerService(@PathVariable("motorhomeID")int id,
                                  @RequestParam(value = "dateFrom")String dateFrom,
                                  @RequestParam(value = "dateTo", required = false) String dateTo,
                                  @RequestParam(value = "lights", required = false) String lights,
                                  @RequestParam(value = "engine", required = false) String engine,
                                  @RequestParam(value = "chasis", required = false) String chassis,
                                  @RequestParam(value = "interior", required = false) String interior,
                                  @RequestParam(value = "exterior", required = false) String exterior,
                                  @RequestParam(value = "description", required = false) String description,
                                  @RequestParam(value = "amount", required = false) String amount,
                                  Model model){

        Service service = new Service();
        service.setMotorhouseID(id);

        if(dateFrom.equals("")) {
            model.addAttribute("error", "Start date not present.");
            model.addAttribute("motorhome", motorHouseRepository.get(id));
            return defaultFilePath + "/register";
        }
        if(!dateTo.equals("")) {
            String[] dateSplit = dateTo.split("-");
            LocalDate dateto = LocalDate.of(Integer.parseInt(dateSplit[0]),
                    Integer.parseInt(dateSplit[1]),
                    Integer.parseInt(dateSplit[2]));
            dateSplit = dateFrom.split("-");
            LocalDate datefrom = LocalDate.of(Integer.parseInt(dateSplit[0]),
                    Integer.parseInt(dateSplit[1]),
                    Integer.parseInt(dateSplit[2]));

            if(ChronoUnit.DAYS.between(datefrom, dateto) < 0){
                model.addAttribute("error", "Due date cannot be earlier than start date. ");
                model.addAttribute("motorhome", motorHouseRepository.get(id));
                return defaultFilePath + "/register";
            }
            service.setDateTo(dateTo);
        }
        service.setDateFrom(dateFrom);
        service.setDescription(description);

        if(amount != null && !amount.equals(""))
            service.setAmount(Double.parseDouble(amount));

        if(lights != null)
            service.setLights(lights.equals("OK"));
        else
            service.setLights(false);
        if(engine != null)
            service.setEngine(engine.equals("OK"));
        else
            service.setEngine(false);
        if(chassis != null)
            service.setChassis(chassis.equals("OK"));
        else
            service.setChassis(false);
        if(interior != null)
            service.setInterior(interior.equals("OK"));
        else
            service.setInterior(false);
        if(exterior != null)
            service.setExterior(exterior.equals("OK"));
        else
            service.setExterior(false);

        serviceRepository.create(service);

        return "redirect:" + defaultPath;
    }

    @GetMapping(defaultPath + "/edit/{serviceID}")
    public String editService(@PathVariable("serviceID")int id, Model model){
        model.addAttribute("service", serviceRepository.get(id));
        return defaultFilePath + "edit";
    }

    @PostMapping(defaultPath + "/edit/{serviceID}")
    public String editService(@PathVariable("serviceID")int id,
                              @RequestParam(value = "dateFrom")String dateFrom,
                              @RequestParam(value = "dateTo", required = false) String dateTo,
                              @RequestParam(value = "lights", required = false)String lights,
                              @RequestParam(value = "engine", required = false)String engine,
                              @RequestParam(value = "chasis", required = false)String chassis,
                              @RequestParam(value = "interior", required = false)String interior,
                              @RequestParam(value = "exterior", required = false)String exterior,
                              @RequestParam(value = "description", required = false)String description,
                              @RequestParam(value = "amount", required = false)String amount){

        Service service = new Service();

        if(dateTo != null && !dateTo.equals(""))
            service.setDateTo(dateTo);
        service.setId(id);
        service.setDateFrom(dateFrom);
        service.setDescription(description);
        service.setAmount(Double.parseDouble(amount));

        if(lights != null)
            service.setLights(lights.equals("OK"));
        else
            service.setLights(false);
        if(engine != null)
            service.setEngine(engine.equals("OK"));
        else
            service.setEngine(false);
        if(chassis != null)
            service.setChassis(chassis.equals("OK"));
        else
            service.setChassis(false);
        if(interior != null)
            service.setInterior(interior.equals("OK"));
        else
            service.setInterior(false);
        if(exterior != null)
            service.setExterior(exterior.equals("OK"));
        else
            service.setExterior(false);

        serviceRepository.update(service);

        return "redirect:" + defaultPath;
    }
}
