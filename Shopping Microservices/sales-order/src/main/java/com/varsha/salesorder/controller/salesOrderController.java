package com.varsha.salesorder.controller;

import java.util.List;

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

import com.varsha.salesorder.entity.OrderInput;
import com.varsha.salesorder.entity.OrderOutput;
import com.varsha.salesorder.exception.CustomerNotFoundException;
import com.varsha.salesorder.exception.EmptyCartException;
import com.varsha.salesorder.exception.ItemNotFoundException;
import com.varsha.salesorder.exception.OrderNotFoundException;
import com.varsha.salesorder.service.salesOrderService;


@RestController
@RequestMapping("/orders")
@CrossOrigin("*")
public class salesOrderController {

	@Autowired
	private salesOrderService salesOrderService;
	
	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome to Order Service.";
	}
	
	@PostMapping("/save")
	public ResponseEntity<OrderOutput> saveOrder(@RequestBody OrderInput orderInput) throws ItemNotFoundException, EmptyCartException{
		OrderOutput orderOutput = salesOrderService.saveOrder(orderInput);
		if(orderOutput == null)
			return new ResponseEntity<OrderOutput>(orderOutput, HttpStatus.GATEWAY_TIMEOUT);
		else
			return new ResponseEntity<OrderOutput>(orderOutput, HttpStatus.CREATED);
	}
	
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<List<OrderOutput>> findSalesOrderByCustomerId(@PathVariable Long customerId) throws CustomerNotFoundException{
		List<OrderOutput> output = salesOrderService.findSalesOrderByCustomerId(customerId);
		if(output.size() == 0)
			return new ResponseEntity<>(output, HttpStatus.GATEWAY_TIMEOUT);
		else
			return new ResponseEntity<>(output, HttpStatus.OK);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderOutput> findSalesOrderById(@PathVariable("orderId") Long orderId) throws OrderNotFoundException{
		OrderOutput orderOutput = salesOrderService.findSalesOrderByOrderId(orderId);
		if(orderOutput == null)
			return new ResponseEntity<OrderOutput>(orderOutput, HttpStatus.GATEWAY_TIMEOUT);
		else
			return new ResponseEntity<OrderOutput>(orderOutput, HttpStatus.OK);
	}
}
