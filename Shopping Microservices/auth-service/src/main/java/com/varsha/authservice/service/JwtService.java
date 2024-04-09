package com.varsha.authservice.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.varsha.authservice.entity.CustomerProfile;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${secretkey}")
	private String SECRETKEY;

	@Value("${tokenduration}")
	private int TOKENDURATION;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		String formated_token = token.trim().replaceAll("\0xfffd", "");
//		return Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(formated_token).getBody();
		return Jwts.parserBuilder().setSigningKey(SECRETKEY).build().parseClaimsJws(formated_token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(CustomerProfile customer) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, customer.getUsername());
	}

	private String createToken(Map<String, Object> claims, String username) {

		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TOKENDURATION * 60 * 1000))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}
	private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRETKEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

	public Boolean validateToken(String token, CustomerProfile customer) {
		final String username = extractUsername(token);
		return (username.equals(customer.getUsername()) && !isTokenExpired(token));
	}
}
