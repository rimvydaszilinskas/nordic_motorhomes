package com.example.nordicmotorhomes.controllers;

import com.example.nordicmotorhomes.models.Customer;
import com.example.nordicmotorhomes.repositories.CustomerRepository;
import com.example.nordicmotorhomes.repositories.util.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomerController {
    private CustomerRepository customerRepository = new CustomerRepository();
    private List<Customer> customers;

    @GetMapping("/customers")
    public String index(Model model){
        customers = customerRepository.getAll();
        model.addAttribute("customers", customers);
        return "customers/index";
    }

    @GetMapping("/customers/details/{id}")
    public String details(Model model, @PathVariable("id") int id){
        Customer customer = customerRepository.get(id);

        model.addAttribute("customer", customer);

        return "customers/display";
    }

    @GetMapping("/customers/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id){
        Customer customer = customerRepository.get(id);

        model.addAttribute("customer", customer);

        return "customers/edit";
    }

    @PostMapping("/customers/edit")
    public String edit(@ModelAttribute Customer customer){
        customerRepository.update(customer);
        return "redirect:/customers";
    }

    @PostMapping("/customers/details")
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

    @GetMapping("/customers/create")
    public String create(){
        return "customers/new";
    }

    @PostMapping("/customers/create")
    public String createCustomer(@ModelAttribute Customer customer){
        customerRepository.create(customer);

        return "redirect:/customers";
    }

    @GetMapping("/customers/delete/{id}")
    public String delete(@PathVariable("id")int id){
        customerRepository.delete(id);

        return "redirect:/customers";
    }

    @PostMapping("/customers/update")
    public String update(@ModelAttribute Customer customer){
        customerRepository.update(customer);

        return "redirect:/customers";
    }
}
