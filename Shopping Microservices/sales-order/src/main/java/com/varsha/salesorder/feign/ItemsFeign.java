package com.varsha.salesorder.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.varsha.salesorder.entity.Item;

//@FeignClient(name = "ITEMS-SERVICE", url = "http://localhost:7070")
@FeignClient(name = "ITEMS-SERVICE")
public interface ItemsFeign {
	
	@GetMapping("/items/{name}")
	public ResponseEntity<Item> findItemByName(@PathVariable String name);

}
