package com.varsha.authservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.varsha.authservice.entity.CustomerProfile;


//@FeignClient(name = "CUSTOMER-SERVICE", url = "http://localhost:7050")
@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerFeign {

	@GetMapping("/customer/{username}")
	public ResponseEntity<CustomerProfile> findCustomerByUsername(@PathVariable String username);
}
