package com.varsha.authservice.entity;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
public class AuthRequest {

	private String username;
	
	private String password;
}
