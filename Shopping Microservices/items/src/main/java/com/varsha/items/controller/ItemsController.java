package com.varsha.items.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.varsha.items.entity.Item;
import com.varsha.items.service.ItemsService;

@RestController
@RequestMapping("/items")
public class ItemsController {

	private final Logger logger = LoggerFactory.getLogger(ItemsController.class);
	
	@Autowired
	private ItemsService itemsService;
	
	@GetMapping("/welcome")
	public String welcome() {
		logger.info("Items service testing...");
		return "Welcome to Items service";
	}
	
	@PostMapping("/save")
	public ResponseEntity<Item> saveCustomer(@RequestBody Item item){
		logger.info("Saving new Item...");
		return new ResponseEntity<>(itemsService.saveItem(item), HttpStatus.CREATED);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Item>> findAllItems(){
		logger.info("Retrieving all items...");
		return new ResponseEntity<>(itemsService.findAllItems(), HttpStatus.OK);
	}
	
	@GetMapping("/{name}")
	public ResponseEntity<Item> findItemByName(@PathVariable String name){
		logger.info("Retrieving item by name...");
		return new ResponseEntity<Item>(itemsService.findItemByName(name), HttpStatus.OK);
	}
}
