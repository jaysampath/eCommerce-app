package com.services.eCommerceRestful.rest;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.services.eCommerceRestful.entity.Order;
import com.services.eCommerceRestful.entity.OrderItem;
import com.services.eCommerceRestful.service.EcommerceService;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderRestController {
	
	@Autowired
	private EcommerceService eCommerceService;
	
	
	@PostMapping("/orders")
	public CompletableFuture<Order> saveOrder(@RequestBody Order order) {
		CompletableFuture<Order> insertedOrder = eCommerceService.saveOrder(order);
		return insertedOrder;
	}
	
	@GetMapping("/orders/{userEmail:.+}")
	public List<Order> getAllOrders(@PathVariable String userEmail){
		List<Order> orders = eCommerceService.getAllOrders(userEmail);
		return orders;
	}
	
	@GetMapping("/orders/id/{orderId}")
	public Order getOrderById(@PathVariable int orderId) {
		Order order = eCommerceService.getOrder("", orderId);
		return order;
	}
	
	@GetMapping("/orders/id/items/{orderId}")
	public List<OrderItem> getOrderItems(@PathVariable int orderId){
		List<OrderItem> orderItems = eCommerceService.getOrderItems("", orderId);
		
		return orderItems;
	}

}
