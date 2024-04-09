package com.varsha.authservice.entity;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
}
