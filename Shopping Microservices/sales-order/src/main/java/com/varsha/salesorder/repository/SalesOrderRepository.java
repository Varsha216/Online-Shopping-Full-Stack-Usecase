package com.varsha.salesorder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.varsha.salesorder.entity.OrderOutput;
import com.varsha.salesorder.entity.SalesOrder;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long>{

	SalesOrder findSalesOrderByOrderId(Long orderId);

	List<SalesOrder> findSalesOrderByCustomerId(Long customerId);

}
