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
import java.util.LinkedList;
import java.util.List;

@Controller
public class ReservationController {
    //all paths should start with /reservation
    private static final String defaultPath = "/reservation";
    private static final String defaultFilePath = "reservations/";

    private IReservation reservationRepository = new ReservationRepository();
    private IMotorHouse motorHouseRepo = new MotorHouseRepository();
    private IPerson<Customer> customerRepository = new PersonRepository();
    private IPayment paymentRepo = new PaymentRepository();
    private IExtras extraRepo = new ExtrasRepository();
    private IPickupDropoff pickupRepo = new PickupDropOffRepository();

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
        List<MotorHouse> motorHouses = new MotorHouseRepository().getAllFreeMotorhouses(dateFrom, dateTo, Integer.parseInt(seats), Integer.parseInt(beds));
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
        model.addAttribute("extras", extraRepo.getAll());
        return defaultFilePath + "create";
    }

    @PostMapping(defaultPath + "/create")
    public String createReservation(@RequestParam("dateFrom")String dateFrom,
                                    @RequestParam("dateTo")String dateTo,
                                    @RequestParam("motorhouseID")String motorhouseID,
                                    @RequestParam("customer")String customerID,
                                    @RequestParam("extras")String extras,
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

        List<Integer> ids = new LinkedList<>();
        if(!extras.equals("default")){
            String[] split = extras.split(",");

            for(String extraID : split){
                ids.add(Integer.parseInt(extraID));
            }
        }

        Reservation reservation = new Reservation(0);
        reservation.setDateFrom(dateFrom);
        reservation.setDateTo(dateTo);
        reservation.setMotorhouseID(Integer.parseInt(motorhouseID));
        reservation.setCustomerID(Integer.parseInt(customerID));
        reservation.setStatus("booked");
        reservation.setTotal(days * PriceCalculator.GetPrice(motorHouseRepo.get(Integer.parseInt(motorhouseID)).getPrice()) + extraRepo.inRangeTotal(ids));
        int id = reservationRepository.createGetID(reservation);
        if(id != 0)
            model.addAttribute("status", true);
        else
            model.addAttribute("status", false);

        if(!extras.equals("default")){
            extraRepo.createReservationExtras(id, ids);
        }

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
        model.addAttribute("extras", extraRepo.getReservation(id));
        model.addAttribute("paid", paid);
        return defaultFilePath + "details";
    }

    @PostMapping(defaultPath + "/pay")
    public String registerPayment(@RequestParam("id")int id,
                                  @RequestParam("ammount")String ammount,
                                  @RequestParam("description")String description,
                                  Model model){
        Reservation reservation = reservationRepository.get(id);
        List<Payment> payments = paymentRepo.getReservationPayments(id);
        double ammountPaid = Double.parseDouble(ammount);
        double totalPaid = 0;

        if(ammountPaid < 5){
            return "redirect:" + defaultPath + "/details/" + id;
        }

        for(Payment payment : payments){
            totalPaid += payment.getAmmount();
        }

        if(totalPaid + ammountPaid > reservation.getTotal()){
            double change = ammountPaid - (reservation.getTotal() - totalPaid);
            ammountPaid -= change;

            if(ammountPaid < 5){
                return "redirect:" + defaultPath + "/details/" + id;
            }

            model.addAttribute("ammountPaid", ammountPaid);
            model.addAttribute("change",  change);
            model.addAttribute("hasChange", true);
        }



        Payment payment = new Payment();
        payment.setAmmount(ammountPaid);
        payment.setReservation_id(id);
        payment.setDescription(description);
        payment.setDate(LocalDate.now());

        if(paymentRepo.add(payment)){
            model.addAttribute("success", true);
            model.addAttribute("message", "Payment successful.");
        } else {
            model.addAttribute("success", false);
            model.addAttribute("message", "Payment could not be done.");
        }

        return "redirect:" + defaultPath + "/details/" + id;
    }

    @GetMapping(defaultPath + "/cancel/{id}")
    public String cancellation(@PathVariable("id")int id, Model model){
        Reservation reservation = reservationRepository.get(id);
        long days = ChronoUnit.DAYS.between(reservation.getDateFrom(), LocalDate.now()) * (-1);
        if(days >= 50){
            //20% but minimum 200EUR.
            double total = 0.2 * reservation.getTotal();
            if(total >= 200)
                reservation.setTotal(total);
            else
                reservation.setTotal(200);
        } else if(days >= 15){
            //50%
            reservation.setTotal(reservation.getTotal() * 0.5);
        } else if(days > 1){
            //80%
            reservation.setTotal(reservation.getTotal() * 0.8);
        } else {
            //95%
            reservation.setTotal(reservation.getTotal() * 0.95);
        }
        //check if customer paid enough already
        List<Payment> payments = paymentRepo.getReservationPayments(id);
        double total = 0;

        for(Payment payment : payments){
            total += payment.getAmmount();
        }

        if(total > reservation.getTotal()){
            double repay = (total - reservation.getTotal()) * (-1);
            paymentRepo.add(new Payment(repay, id, LocalDate.now(), "Cancellation repay"));
            model.addAttribute("repay", true);
            model.addAttribute("repayMsg", "Repay the customer " + (total - reservation.getTotal()) + " EUR.");
        } else if(total < reservation.getTotal()){
            model.addAttribute("askRepay", true);
            model.addAttribute("repayMsg", "Customer has to pay " + (reservation.getTotal() - total) + " EUR.");
        }

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
        model.addAttribute("extraDistance", distance);
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
        pickupRepo.create(new Pickup(LocalDate.now(), 1, id, motorHouseRepo.getReservation(id).getMileage()));
        return "redirect:" + defaultPath;
    }

    @PostMapping(defaultPath + "/pickup/confirmaddress")
    public String confirmPickup(@RequestParam("id")int id, @RequestParam("extra")String extra, @RequestParam("extraDistance")double distance){
        double extraPrice = Double.parseDouble(extra);
        Reservation reservation = reservationRepository.get(id);
        reservation.setTotal(reservation.getTotal() + extraPrice);
        reservation.setStatus("taken");
        reservationRepository.update(reservation);
        pickupRepo.create(new Pickup(LocalDate.now(),
                1,
                id,
                motorHouseRepo.getReservation(id).getMileage() + (int) distance));

        return "redirect:" + defaultPath;
    }

    @GetMapping(defaultPath + "/dropoff/{id}")
    public String dropOff(@PathVariable("id")int id, Model model){
        model.addAttribute("id", id);
        return defaultFilePath + "dropofflocation";
    }

    @GetMapping(defaultPath + "/dropoff/skip/{id}")
    public String dropOffDetails(@PathVariable("id")int id, Model model){
        //navigate to enter the km
        model.addAttribute("id", id);
        model.addAttribute("minMileage", motorHouseRepo.getReservation(id).getMileage());
        return defaultFilePath + "confirmDropoff";
    }

    @PostMapping(defaultPath + "/dropoff/location")
    public String dropOffDetails(@RequestParam("id")int id,
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
        MotorHouse motorHouse = motorHouseRepo.getReservation(id);

        model.addAttribute("distanceMsg", "By pressing confirm you confirm that the drop off will be at " + fullAdress.toUpperCase() + " for extra " + decimalFormat.format(price) + " EUR.");
        model.addAttribute("extraDistance", distance);
        model.addAttribute("id", id);
        model.addAttribute("minMileage", motorHouse.getMileage() + distance);
        return defaultFilePath + "confirmDropoff";
    }

    @PostMapping(defaultPath + "/dropoff/confirm")
    public String dropOffConfirm(@RequestParam("id") int id,
                                 @RequestParam("fuel") String fuelLevel,
                                 @RequestParam("mileage")int mileage){
        double fuel = Double.parseDouble(fuelLevel);

        MotorHouse motorHouse = motorHouseRepo.getReservation(id);
        motorHouse.setMileage(mileage);
        motorHouseRepo.update(motorHouse);

        Reservation reservation = reservationRepository.get(id);
        if(1 - fuel < 0.5){
            reservation.setTotal(reservation.getTotal() + 70);
        }

        Pickup pickup = pickupRepo.getReservationPickups(id).get(0);
        int km_start = pickup.getMileage();
        long days = ChronoUnit.DAYS.between(reservation.getDateFrom(), reservation.getDateTo());

        //check the average km/day
        if (days == 0){
            days = 1;
        }
        if((mileage - km_start) / days > 400){
            double difference = mileage - km_start - (days * 400);
            reservation.setTotal(reservation.getTotal() + (int) difference);
        }

        List<Payment> payments = paymentRepo.getReservationPayments(id);

        double paid = 0;
        for(Payment payment : payments){
            paid += payment.getAmmount();
        }

        if(paid >= reservation.getTotal()){
            reservation.setStatus("finished");
        } else {
            reservation.setStatus("pending");
        }
        return "redirect: " + defaultPath;
    }

    @PostMapping(defaultPath + "/dropoff/confirmaddress")
    public String dropOffConfirm(@RequestParam("id")int id,
                                 @RequestParam("extraDistance")String extraDistance,
                                 @RequestParam("fuel")String fuelLevel,
                                 @RequestParam("mileage")int mileage){
        double fuel = Double.parseDouble(fuelLevel);

        MotorHouse motorHouse = motorHouseRepo.getReservation(id);
        if(!extraDistance.equals("")) {
            int distance = Integer.parseInt(extraDistance);
            motorHouse.setMileage(mileage + distance);
        } else {
            motorHouse.setMileage(mileage);
        }
        motorHouseRepo.update(motorHouse);

        Reservation reservation = reservationRepository.get(id);
        if(1 - fuel < 0.5){
            reservation.setTotal(reservation.getTotal() + 70);
        }

        Pickup pickup = pickupRepo.getReservationPickups(id).get(1);
        int km_start = pickup.getMileage();
        long days = ChronoUnit.DAYS.between(reservation.getDateFrom(), reservation.getDateTo());

        //check the average km/day
        if((mileage - km_start) / days > 400){
            double difference = mileage - km_start - (days * 400);
            reservation.setTotal(reservation.getTotal() + (int) difference);
        }

        List<Payment> payments = paymentRepo.getReservationPayments(id);

        double paid = 0;
        for(Payment payment : payments){
            paid += payment.getAmmount();
        }

        if(paid >= reservation.getTotal()){
            reservation.setStatus("finished");
        } else {
            reservation.setStatus("pending");
        }
        return "redirect:" + defaultPath;
    }
}