package com.example.nordicmotorhomes.controllers;

import com.example.nordicmotorhomes.models.Customer;
import com.example.nordicmotorhomes.models.Reservation;
import com.example.nordicmotorhomes.repositories.PersonRepository;
import com.example.nordicmotorhomes.repositories.IPerson;
import com.example.nordicmotorhomes.repositories.ReservationRepository;
import com.example.nordicmotorhomes.utilities.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomerController {
    private IPerson<Customer> customerRepository = new PersonRepository();
    private List<Customer> customers;

    private static final String defaultPath = "/customers";
    private static final String defaultFilePath = "customers/";

    @GetMapping(defaultPath)
    public String index(Model model){
        customers = customerRepository.getAll();
        model.addAttribute("customers", customers);
        return defaultFilePath + "index";
    }

    @GetMapping(defaultPath + "/details/{id}")
    public String details(Model model, @PathVariable("id") int id){
        Customer customer = customerRepository.get(id);
        List<Reservation> reservations = new ReservationRepository().getCustomerReservation(id);
        model.addAttribute("customer", customer);
        model.addAttribute("reservations", reservations);
        return defaultFilePath + "display";
    }

    @GetMapping(defaultPath + "/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id){
        Customer customer = customerRepository.get(id);

        model.addAttribute("customer", customer);

        return defaultFilePath + "edit";
    }

    @PostMapping(defaultPath + "/edit")
    public String edit(@ModelAttribute Customer customer){
        customerRepository.update(customer);
        return "redirect:" + defaultPath;
    }

    @PostMapping(defaultPath + "/details")
    @ResponseBody
    public String details(@RequestParam("id")int id){
        JSON json = new JSON();

        Customer customer = customerRepository.get(id);

        json.add("name", customer.getFirstName() + " " + customer.getLastName());
        json.add("id", customer.getId());
        json.add("cpr", customer.getCPR());
        json.add("phone", customer.getPhone());
        json.add("address", customer.getAddress());
        json.add("postal", customer.getPostCode());
        json.add("city", customer.getCity());
        json.add("country", customer.getCountry());

        return json.getJSON();
    }

    @GetMapping(defaultPath + "/create")
    public String create(){
        return defaultFilePath + "new";
    }

    @PostMapping(defaultPath + "/create")
    public String createCustomer(@ModelAttribute Customer customer){
        customerRepository.create(customer);

        return "redirect:" + defaultPath;
    }

    @GetMapping(defaultPath + "/delete/{id}")
    public String delete(@PathVariable("id")int id){
        customerRepository.delete(id);

        return "redirect:" + defaultPath;
    }

    @PostMapping(defaultPath + "/update")
    public String update(@ModelAttribute Customer customer){
        customerRepository.update(customer);

        return "redirect:" + defaultPath;
    }

    @PostMapping(defaultPath + "/filter")
    public String filter(@RequestParam("name") String name, Model model){
        customers = customerRepository.getLike(name);
        model.addAttribute("customers", customers);
        return defaultFilePath + "index";
    }
}
