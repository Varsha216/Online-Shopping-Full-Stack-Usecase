package com.varsha.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.varsha.customer.constant.AppConstant;
import com.varsha.customer.entity.Customer;
import com.varsha.customer.entity.CustomerProfile;
import com.varsha.customer.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private KafkaTemplate<String, Customer> kafkaTemplate;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	// Password Encoding 
    @Bean
    public PasswordEncoder passwordEncoder() { 
        return new BCryptPasswordEncoder(); 
    } 
	
	public CustomerProfile saveCustomer(CustomerProfile customerProfile) {
		customerProfile.setPassword(passwordEncoder().encode(customerProfile.getPassword()));
		CustomerProfile savedCustomer = customerRepository.save(customerProfile);
		
		Customer customer = new Customer();
		customer.setId(savedCustomer.getId());
		customer.setFirstName(savedCustomer.getFirstName());
		customer.setLastName(savedCustomer.getLastName());
		customer.setEmail(savedCustomer.getEmail());
		
		kafkaTemplate.send(AppConstant.CREATED_CUSTOMER, customer);
		return savedCustomer;
	}

	public List<CustomerProfile> findAllCustomers() {
		return customerRepository.findAll();
	}

	public CustomerProfile findCustomerById(Long id) {
		return customerRepository.findCustomerProfileById(id);
	}

	public CustomerProfile findCustomerByUsername(String username) {
		return customerRepository.findCustomerProfileByUsername(username);
	}

}
