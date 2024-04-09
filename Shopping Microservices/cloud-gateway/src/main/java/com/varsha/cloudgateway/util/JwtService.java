package com.varsha.cloudgateway.util;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String SECRETKEY = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
	
	public void validateToken(String token) {
		Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
	}
	
	private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRETKEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

	
}
