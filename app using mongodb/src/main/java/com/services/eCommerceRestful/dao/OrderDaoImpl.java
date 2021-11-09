package com.services.eCommerceRestful.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.services.eCommerceRestful.entity.Order;
import com.services.eCommerceRestful.entity.OrderItem;

@Repository
public class OrderDaoImpl implements OrderDao {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public Order saveOrder(Order order) {
		// TODO Auto-generated method stub
		mongoTemplate.insert(order, "customer_order");
		return order;
	}

	@Override
	public List<OrderItem> getOrderItems(String userEmail, int orderId) {
		// TODO Auto-generated method stub
		Order order = mongoTemplate.findById(orderId, Order.class);
		List<OrderItem> orderItems = order.getOrderItems();		
		return orderItems;
	}

	@Override
	public Order getOrder(String userEmail, int orderId) {
		// TODO Auto-generated method stub
		Order order = mongoTemplate.findById(orderId, Order.class);
		return order;
	}

	@Override
	public List<Order> getAllOrders(String userEmail) {
		// TODO Auto-generated method stub
		Query query = new Query();
		query.addCriteria(Criteria.where("userEmail").is(userEmail) );
		List<Order> orders = mongoTemplate.find(query, Order.class);		
		return orders;
	}

}
