package com.varsha.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.varsha.authservice.entity.CustomerProfile;
import com.varsha.authservice.exception.CustomerNotFoundException;
import com.varsha.authservice.feign.CustomerFeign;

@Service
public class CustomerService implements UserDetailsService{

	@Autowired
	private CustomerFeign customerFeign;

	@Override
	public CustomerProfile loadUserByUsername(String username) throws UsernameNotFoundException{
		CustomerProfile customer = null;
		try {
			customer = getCustomerByUserName(username);
		} catch (CustomerNotFoundException e) {
			e.printStackTrace();
		}
		return customer;
	}
	
	
	private CustomerProfile getCustomerByUserName(String username) throws CustomerNotFoundException {
		CustomerProfile customer = null;
		customer = customerFeign.findCustomerByUsername(username).getBody();
		if (customer == null) {
			throw new CustomerNotFoundException("Given Customer-Details does not exist in our Database!!");
		}
		return customer;
	}
	
	
}
