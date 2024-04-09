package com.varsha.cloudgateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.varsha.cloudgateway.util.JwtService;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config>{

	@Autowired
    private RouteValidator validator;
	
	@Autowired
	private JwtService jwtService;
	
	public AuthFilter() {
		super(Config.class);
	}
	
	@Override
	public GatewayFilter apply(Config config){
		return ((exchange, chain) ->{
			ServerHttpRequest mutatedRequest = null;
			
			if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                	
                    throw new RuntimeException("Missing authorization header");
                	
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    throw new RuntimeException("Header doesn't start with Bearer");
                }
                try {
                	String token = authHeader.substring(7);
                	
                	jwtService.validateToken(token);
                    
                    //To add extra info in header to pass it to other microservices
                    mutatedRequest = exchange.getRequest()
                    .mutate()
                    .header("userName", "AnyCustomer")
                    .build();
                    

                } catch (Exception e) {
                    System.out.println("invalid access...! " + e.getMessage());
                    throw new RuntimeException("unauthorized access to application");
                }
            }
			
			ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
			
			return chain.filter(mutatedExchange);
		});
	}
	
	public static class Config {

	}
}
