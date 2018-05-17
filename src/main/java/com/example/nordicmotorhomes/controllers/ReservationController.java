package com.example.nordicmotorhomes.controllers;

import com.example.nordicmotorhomes.models.*;
import com.example.nordicmotorhomes.repositories.*;
import com.example.nordicmotorhomes.utilities.DistanceCounter;
import com.example.nordicmotorhomes.utilities.JSON;
import com.example.nordicmotorhomes.utilities.PriceCalculator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ReservationController {
    //all paths should start with /reservation
    private static final String defaultPath = "/reservation";
    private static final String defaultFilePath = "reservations/";

    private IReservation reservationRepository = new ReservationRepository();
    private IMotorHouse motorHouseRepo = new MotorHouseRepo();
    private IPerson<Customer> customerRepository = new PersonRepository();
    private IPayment paymentRepo = new PaymentRepository();
    private IExtras extraRepo = new ExtrasRepository();

    @GetMapping(defaultPath)
    public String index(Model model){
        model.addAttribute("reservations", reservationRepository.getAll());
        model.addAttribute("today", LocalDate.now());
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
        String[] hold = dateFrom.split("-");
        LocalDate start = LocalDate.of(Integer.parseInt(hold[0]),
                                        Integer.parseInt(hold[1]),
                                        Integer.parseInt(hold[2]));
        hold = dateTo.split("-");

        LocalDate end = LocalDate.of(Integer.parseInt(hold[0]),
                                        Integer.parseInt(hold[1]),
                                        Integer.parseInt(hold[2]));

        long days = ChronoUnit.DAYS.between(start, end);

        model.addAttribute("motorhouses", motorHouses);
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        model.addAttribute("days", days);

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
        MotorHouse motorHouse = motorHouseRepo.get(id);
        motorHouse.setPrice(PriceCalculator.GetPrice(motorHouse.getPrice()));
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        model.addAttribute("motorhouse", motorHouse);
        model.addAttribute("customers", customerRepository.getAll());
        return defaultFilePath + "create";
    }

    @PostMapping(defaultPath + "/create")
    public String createReservation(@RequestParam("dateFrom")String dateFrom,
                                    @RequestParam("dateTo")String dateTo,
                                    @RequestParam("motorhouseID")String motorhouseID,
                                    @RequestParam("customer")String customerID,
                                    Model model){
        String[] hold = dateFrom.split("-");
        LocalDate start = LocalDate.of(Integer.parseInt(hold[0]),
                Integer.parseInt(hold[1]),
                Integer.parseInt(hold[2]));
        hold = dateTo.split("-");

        LocalDate end = LocalDate.of(Integer.parseInt(hold[0]),
                Integer.parseInt(hold[1]),
                Integer.parseInt(hold[2]));

        long days = ChronoUnit.DAYS.between(start, end);

        Reservation reservation = new Reservation(0);
        reservation.setDateFrom(dateFrom);
        reservation.setDateTo(dateTo);
        reservation.setMotorhouseID(Integer.parseInt(motorhouseID));
        reservation.setCustomerID(Integer.parseInt(customerID));
        reservation.setStatus("booked");
        reservation.setTotal(days * PriceCalculator.GetPrice(motorHouseRepo.get(Integer.parseInt(motorhouseID)).getPrice()));

        if(reservationRepository.create(reservation))
            model.addAttribute("status", true);
        else
            model.addAttribute("status", false);
        model.addAttribute("reservations", reservationRepository.getAll());
        model.addAttribute("today", LocalDate.now());
        return defaultFilePath + "index";
    }

    @GetMapping(defaultPath + "/details/{id}")
    public String details(@PathVariable("id")int id, Model model){
        Reservation reservation = reservationRepository.get(id);
        List<Payment> payments = paymentRepo.getReservationPayments(id);
        double paid = 0;
        for(Payment payment : payments){
            paid += payment.getAmmount();
        }
        model.addAttribute("reservation", reservation);
        model.addAttribute("payments", payments);
        model.addAttribute("paid", paid);
        return defaultFilePath + "details";
    }

    @PostMapping(defaultPath + "/pay")
    public String registerPayment(@RequestParam("id")int id,
                                  @RequestParam("ammount")String ammount,
                                  @RequestParam("description")String description){

        Payment payment = new Payment();
        payment.setAmmount(Double.parseDouble(ammount));
        payment.setReservation_id(id);
        payment.setDescription(description);
        payment.setDate(LocalDate.now());

        paymentRepo.add(payment);

        return "redirect:" + defaultPath + "/details/" + id;
    }

    @GetMapping(defaultPath + "/cancel/{id}")
    public String cancellation(@PathVariable("id")int id){
        Reservation reservation = reservationRepository.get(id);
        reservation.setStatus("cancelled");
        reservationRepository.update(reservation);
        return "redirect:" + defaultPath;
    }

    @GetMapping(defaultPath + "/pickup/{id}")
    public String pickUp(@PathVariable("id")int id, Model model){
        model.addAttribute("id", id);
        return defaultFilePath + "pickuplocation";
    }

    @PostMapping(defaultPath + "/pickup/location")
    public String pickUp(@RequestParam("id")int id,
                         @RequestParam("address")String address,
                         @RequestParam("city")String city,
                         @RequestParam("country")String country,
                         Model model){
        String fullAdress = new StringBuilder(address)
                .append(", ")
                .append(city)
                .append(",")
                .append(country)
                .toString();
        double distance = DistanceCounter.getDistance(fullAdress);
        double price = distance * 0.7;
        DecimalFormat decimalFormat = new DecimalFormat(".##");

        model.addAttribute("locationText", "Are you sure you want to confirm pickup at " +
                address.toUpperCase() + ", " + city.toUpperCase() + ", " + country.toUpperCase() + " for extra " + decimalFormat.format(price) + "?");
        model.addAttribute("extraPrice", price);
        model.addAttribute("id", id);

        return defaultFilePath + "pickupConfirm";
    }

    @GetMapping(defaultPath + "/pickup/skip/{id}")
    public String pickUpWithoutLocation(@PathVariable("id")int id, Model model){
        model.addAttribute("locationText", "Are you sure you want to confirm pickup from the office?");
        model.addAttribute("id", id);
        return defaultFilePath + "pickupConfirm";
    }

    @PostMapping(defaultPath + "/pickup/confirm")
    public String confirmPickup(@RequestParam("id")int id){
        reservationRepository.setTaken(id);
        return "redirect:" + defaultPath;
    }

    @PostMapping(defaultPath + "/pickup/confirmaddress")
    public String confirmPickup(@RequestParam("id")int id, @RequestParam("extra")String extra){
        double extraPrice = Double.parseDouble(extra);
        Reservation reservation = reservationRepository.get(id);
        reservation.setTotal(reservation.getTotal() + extraPrice);
        reservation.setStatus("taken");
        reservationRepository.update(reservation);
        return "redirect:" + defaultPath;
    }
}