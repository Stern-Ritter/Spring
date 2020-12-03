package ru.geekbrains.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.persist.Customer;
import ru.geekbrains.persist.CustomerRepository;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public String indexCustomerPage(Model model) {
        logger.info("Customer page update");
        model.addAttribute("customers", customerRepository.findAll());
        return "customer";
    }

    @GetMapping("/{id}")
    public String editCustomer(@PathVariable(value = "id") Long id, Model model) {
        logger.info("Edit customer with id {}", id);
        model.addAttribute("customer", customerRepository.findById(id));
        return "customer_form";
    }

    @GetMapping("/new")
    public String newCustomer(Model model) {
        logger.info("New customer added.");
        Customer customer = new Customer();
        customerRepository.insert(customer);
        model.addAttribute("customer", customer);
        return "customer_form";
    }

    @PostMapping("/update")
    public String updateCustomer(Customer customer) {
        logger.info("Updated customer with id {}", customer.getId());
        customerRepository.update(customer);
        return "redirect:/customer";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable(value = "id") Long id) {
        logger.info("Delete customer with id {}", id);
        customerRepository.delete(id);
        return "redirect:/customer";
    }
}
