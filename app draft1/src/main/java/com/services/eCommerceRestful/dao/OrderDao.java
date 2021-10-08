package com.services.eCommerceRestful.dao;

import java.util.List;

import com.services.eCommerceRestful.entity.Order;
import com.services.eCommerceRestful.entity.OrderItem;

public interface OrderDao {
	
	public Order saveOrder(Order order);
	
	public List<OrderItem> getOrderItems(String userEmail,int orderId);
	
	public Order getOrder(String userEmail, int orderId);
	
	public List<Order> getAllOrders(String userEmail);

}
 