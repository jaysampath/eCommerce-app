package com.services.eCommerceRestful.dao;

import java.util.List;


import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.services.eCommerceRestful.entity.Order;
import com.services.eCommerceRestful.entity.OrderItem;

@Repository
public class OrderDaoImpl implements OrderDao {
	
	private EntityManager entityManager;
	
	public OrderDaoImpl(EntityManager entityManager) {
		this.entityManager=entityManager;
	}

	@Override
	public Order saveOrder(Order order) {
		// TODO Auto-generated method stub
		Session session = entityManager.unwrap(Session.class);
		session.save(order);
		return order;
	}

	@Override
	public List<OrderItem> getOrderItems(String userEmail, int orderId) {
		// TODO Auto-generated method stub
		Session session = entityManager.unwrap(Session.class);
		Query<OrderItem> query = session.createQuery("from OrderItem where order_id=:id");
		query.setParameter("id", orderId);
		List<OrderItem> orderItems = query.getResultList();
		
		return orderItems;
	}

	@Override
	public Order getOrder(String userEmail, int orderId) {
		// TODO Auto-generated method stub
		Session session = entityManager.unwrap(Session.class);
		Order order = session.get(Order.class, orderId);
		return order;
	}

	@Override
	public List<Order> getAllOrders(String userEmail) {
		// TODO Auto-generated method stub
		Session session = entityManager.unwrap(Session.class);
		Query<Order> query =  session.createQuery("from Order where userEmail=:email");
		query.setParameter("email", userEmail);
		List<Order> orders = query.getResultList();
		
		return orders;
	}

}
