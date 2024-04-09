package com.varsha.salesorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.varsha.salesorder.entity.Customer;

@Repository
public interface CustomerSOSRepository extends JpaRepository<Customer, Long>{

}
