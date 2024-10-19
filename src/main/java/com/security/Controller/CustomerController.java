package com.security.Controller;

import com.security.Entity.Customer;
import com.security.Payload.LoginDto;
import com.security.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
// Controller for handling user-related requests
public class CustomerController {

    // Injecting CustomerService to handle customer operations
    @Autowired
    private CustomerService customerService;

    // Endpoint to register a new customer
    @PostMapping("/add")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        // Register the customer and get the created entity
        Customer register = customerService.register(customer);
        // Return the created customer with a CREATED status
        return new ResponseEntity<>(register, HttpStatus.CREATED);
    }

    // Endpoint for customer login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        // Verify login and generate a JWT token
        String token = customerService.VerifyLogin(loginDto);
        // Return the token with an OK status
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    // Endpoint for a simple welcome message
    @GetMapping
    public String getJWTToken() {
        return "Welcome"; // Returns a welcome message
    }
}
