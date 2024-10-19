package com.security.Service;


import com.security.Entity.Customer;
import com.security.Payload.LoginDto;
import com.security.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private JWTService jwtService;

    public Customer register(Customer customer){
        customer.setPassword(BCrypt.hashpw(customer.getPassword(),BCrypt.gensalt(10)));
        Customer save = customerRepository.save(customer);
        return save;
    }

    public String VerifyLogin(LoginDto loginDto){
        Optional<Customer> username = customerRepository.findByEmail(loginDto.getEmail());
        if(username.isPresent()){
            Customer customer = username.get();
            if(BCrypt.checkpw(loginDto.getPassword(),customer.getPassword())) {
                return jwtService.generateToken(customer);
            }
        }
        return null;

    }
}
