package com.varsha.customer.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.varsha.customer.entity.Customer;
import com.varsha.customer.entity.CustomerProfile;
import com.varsha.customer.service.CustomerService;

@CrossOrigin("*")
@RestController
@RequestMapping("/customer")
public class CustomerController {

	private final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	private CustomerService customerService;
	@Autowired
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@GetMapping("/welcome")
	public String welcome() {
		logger.info("Customer service testing...");
		return "Welcome to Customer service...";
	}
	
	@PostMapping("/save")
	public ResponseEntity<CustomerProfile> saveCustomer(@RequestBody CustomerProfile customer){
		logger.info("Saving new Customer...");
		return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.CREATED);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CustomerProfile>> findAllCustomers(){
		logger.info("Retrieving all customer data...");
		return new ResponseEntity<>(customerService.findAllCustomers(), HttpStatus.OK);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<CustomerProfile> findCustomerById(@PathVariable Long id){
		logger.info("Retrieving customer by id...");
		return new ResponseEntity<>(customerService.findCustomerById(id), HttpStatus.OK);
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<CustomerProfile> findCustomerByUsername(@PathVariable String username){
		logger.info("Retrieving customer by username...");
		return new ResponseEntity<>(customerService.findCustomerByUsername(username), HttpStatus.OK);
	}
	
}
