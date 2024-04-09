package com.varsha.salesorder.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.varsha.salesorder.constant.AppConstant;
import com.varsha.salesorder.entity.Customer;
import com.varsha.salesorder.entity.Item;
import com.varsha.salesorder.entity.OrderInput;
import com.varsha.salesorder.entity.OrderLineItem;
import com.varsha.salesorder.entity.OrderOutput;
import com.varsha.salesorder.entity.SalesOrder;
import com.varsha.salesorder.exception.CustomerNotFoundException;
import com.varsha.salesorder.exception.EmptyCartException;
import com.varsha.salesorder.exception.ItemNotFoundException;
import com.varsha.salesorder.exception.OrderNotFoundException;
import com.varsha.salesorder.feign.ItemsFeign;
import com.varsha.salesorder.repository.CustomerSOSRepository;
import com.varsha.salesorder.repository.OrderLineItemRepository;
import com.varsha.salesorder.repository.SalesOrderRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class salesOrderService {

	@Autowired
	private SalesOrderRepository salesOrderRepository;
	
	@Autowired
	private OrderLineItemRepository orderLineItemRepository;
	
	@Autowired
	private CustomerSOSRepository customerSOSRepository;
	
	@Autowired
	private ItemsFeign itemsFeign;
	
	
	@KafkaListener(topics = AppConstant.CREATED_CUSTOMER)
	public void handleKafkaEvent(Customer customer) {
		customerSOSRepository.save(customer);
	}
	
	
	@CircuitBreaker(name = "orderService", fallbackMethod = "saveOrderFallbackMethod")
	public OrderOutput saveOrder(OrderInput orderInput) throws ItemNotFoundException, EmptyCartException{
		if(orderInput.getItemsInCart().size() == 0)
			throw new EmptyCartException("Please add atleast 1 item!");
		
		
		Set<Item> selectedItems = new HashSet<>();
		String errorMsg = "";
		
		for(String str: orderInput.getItemsInCart()) {
			Item item = itemsFeign.findItemByName(str).getBody();
			if(item == null)
				errorMsg = errorMsg + str + ",";
			else
				selectedItems.add(item);
		}
		if(errorMsg != "") {
			errorMsg = errorMsg.substring(0, errorMsg.length()-1);
			throw new ItemNotFoundException("Couldn't found the item in store-> "+ errorMsg);
		}
		
		double totalPrice = selectedItems.stream().mapToDouble(item -> item.getPrice()).sum();
		
		SalesOrder salesOrder = new SalesOrder();
		salesOrder.setOrderDate(orderInput.getOrderDate());
		salesOrder.setOrderDescription(orderInput.getOrderDescription());
		salesOrder.setCustomerId(orderInput.getCustomerId());
		salesOrder.setTotalPrice(totalPrice);
		salesOrder = salesOrderRepository.save(salesOrder);
		
		
		for(Item item: selectedItems) {
			OrderLineItem orderLineItem = new OrderLineItem();
			orderLineItem.setItemName(item.getName());
			orderLineItem.setOrderId(salesOrder.getOrderId());
			orderLineItemRepository.save(orderLineItem);
		}
		
		OrderOutput orderOutput = new OrderOutput();
		orderOutput.setOrderId(salesOrder.getOrderId());
		orderOutput.setOrderDescription(salesOrder.getOrderDescription());
		orderOutput.setTotalPrice(salesOrder.getTotalPrice());
		orderOutput.setSelectedItems(selectedItems);
		
		return orderOutput;
	}

	@CircuitBreaker(name = "orderService", fallbackMethod = "findByCustomerFallbackMethod")
	public List<OrderOutput> findSalesOrderByCustomerId(Long customerId) throws CustomerNotFoundException {
		List<SalesOrder> salesOrders = salesOrderRepository.findSalesOrderByCustomerId(customerId);
		if(salesOrders == null)
			throw new CustomerNotFoundException("Customer not found!");
		
		List<OrderOutput> orderOutputs = new ArrayList<>();
		
		for(SalesOrder salesOrder: salesOrders) {
			Set<Item> selectedItems = new HashSet<>();
			List<OrderLineItem> list = orderLineItemRepository.findByOrderId(salesOrder.getOrderId());
			for(OrderLineItem orderLineItem: list) {
				selectedItems.add(itemsFeign.findItemByName(orderLineItem.getItemName()).getBody());
			}
			
			OrderOutput orderOutput = new OrderOutput();
			orderOutput.setOrderId(salesOrder.getOrderId());
			orderOutput.setOrderDescription(salesOrder.getOrderDescription());
			orderOutput.setTotalPrice(salesOrder.getTotalPrice());
			orderOutput.setOrderDate(salesOrder.getOrderDate());
			orderOutput.setSelectedItems(selectedItems);
			
			orderOutputs.add(orderOutput);
		}
		return orderOutputs;
	}

	@CircuitBreaker(name = "orderService", fallbackMethod = "findByOrderFallbackMethod")
	public OrderOutput findSalesOrderByOrderId(Long orderId) throws OrderNotFoundException {
		Set<Item> selectedItems = new HashSet<>();
		SalesOrder salesOrder = salesOrderRepository.findSalesOrderByOrderId(orderId);
		if(salesOrder == null)
			throw new OrderNotFoundException("Order not found!");
		
		List<OrderLineItem> list = orderLineItemRepository.findByOrderId(salesOrder.getOrderId());
		for(OrderLineItem orderLineItem: list) {
			selectedItems.add(itemsFeign.findItemByName(orderLineItem.getItemName()).getBody());
		}
		
		OrderOutput orderOutput = new OrderOutput();
		orderOutput.setOrderId(salesOrder.getOrderId());
		orderOutput.setOrderDescription(salesOrder.getOrderDescription());
		orderOutput.setTotalPrice(salesOrder.getTotalPrice());
		orderOutput.setOrderDate(salesOrder.getOrderDate());
		orderOutput.setSelectedItems(selectedItems);
		return orderOutput;
	}
	
	private OrderOutput saveOrderFallbackMethod(OrderInput orderInput, Exception e) {
		return null;
	}
	
	private List<OrderOutput> findByCustomerFallbackMethod(Long customerId, Exception e) {
		return null;
	}
	
	private OrderOutput findByOrderFallbackMethod(Long orderId, Exception e) {
		return null;
	}
}
