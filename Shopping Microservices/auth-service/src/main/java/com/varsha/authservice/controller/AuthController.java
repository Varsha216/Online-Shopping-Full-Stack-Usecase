package com.varsha.authservice.controller;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.varsha.authservice.entity.AuthRequest;
import com.varsha.authservice.entity.AuthResponse;
import com.varsha.authservice.entity.CustomerProfile;
import com.varsha.authservice.exception.CustomerNotFoundException;
import com.varsha.authservice.service.CustomerService;
import com.varsha.authservice.service.JwtService;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@Slf4j
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private JwtService jwtService;
	
	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome to Authentication service...";
	}
	
	
	@PostMapping("/authenticate")
	public ResponseEntity<String> generateJwt(@RequestBody AuthRequest request) throws CustomerNotFoundException {
		ResponseEntity<String> response = null;
		
		// authenticating the User-Credentials
		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		if(authenticate.isAuthenticated()) {
			final CustomerProfile customerProfile = customerService.loadUserByUsername(request.getUsername());
		
			final String jwt = jwtService.generateToken(customerProfile);
		
			// test
			log.info("Authenticated User :: {}", customerProfile);
			response = new ResponseEntity<String>(jwt, HttpStatus.OK);
		}
		else{
			throw new CustomerNotFoundException("Not authorized Customer");
		}
		
		return response;
	}
	
	
	@GetMapping("/validate")
	public ResponseEntity<AuthResponse> validateJwt(@RequestHeader("Authorization") String jwt) {

		ResponseEntity<AuthResponse> response = null;
		AuthResponse authResponse = new AuthResponse((long) 0,"", "", "");

		// first remove Bearer from Header
		jwt = jwt.substring(7);
		
		// check the jwt is proper or not
		final CustomerProfile customerProfile = customerService.loadUserByUsername(jwtService.extractUsername(jwt));
	
		try {
			if (jwtService.validateToken(jwt, customerProfile)){
				authResponse.setId(customerProfile.getId());
				authResponse.setFirstName(customerProfile.getFirstName());
				authResponse.setLastName(customerProfile.getLastName());
				authResponse.setEmail(customerProfile.getEmail());
				response = new ResponseEntity<>(authResponse, HttpStatus.OK);
			}
			else
				response = new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			response = new ResponseEntity<AuthResponse>(authResponse, HttpStatus.FORBIDDEN);
			log.error("Exception occured while validating JWT : Exception info : {}", e.getMessage());
		}
		
		
		return response;
	}
}
