package com.varsha.items.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.varsha.items.entity.Item;

@Repository
public interface ItemsRepository extends JpaRepository<Item, Long>{

	Item findItemByName(String name);

}
