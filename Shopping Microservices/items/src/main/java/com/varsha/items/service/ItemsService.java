package com.varsha.items.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.varsha.items.entity.Item;
import com.varsha.items.repository.ItemsRepository;

@Service
public class ItemsService {

	@Autowired
	private ItemsRepository itemsRepository;
	
	@Autowired
	Environment environment;
	
	public Item saveItem(Item item) {
		return itemsRepository.save(item);
	}
	
	public List<Item> findAllItems() {
		return itemsRepository.findAll();
	}

	public Item findItemByName(String name) {
		System.out.println("Port=== "+ environment.getProperty("local.server.port"));
		return itemsRepository.findItemByName(name);
	}

}
