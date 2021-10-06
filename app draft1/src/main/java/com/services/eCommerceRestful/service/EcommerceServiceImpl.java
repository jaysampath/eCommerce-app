package com.services.eCommerceRestful.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.eCommerceRestful.dao.CartDao;
import com.services.eCommerceRestful.dao.ItemsDao;
import com.services.eCommerceRestful.dao.OrderDao;
import com.services.eCommerceRestful.entity.Cart;
import com.services.eCommerceRestful.entity.CartItem;
import com.services.eCommerceRestful.entity.Item;
import com.services.eCommerceRestful.entity.Order;
import com.services.eCommerceRestful.entity.OrderItem;
import com.services.eCommerceRestful.helpers.ItemActionException;
import com.services.eCommerceRestful.helpers.OrderActionException;

@Service
@Transactional
public class EcommerceServiceImpl implements EcommerceService {
	
	@Autowired
	private ItemsDao itemsDao;
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private CartDao cartDao;

	@Override
	@Transactional
	public List<Item> getAllItems() {
		// TODO Auto-generated method stub
		List<Item> items = itemsDao.getAllItems();
		return items;
	}

	@Override
	public Item addNewItem(Item item) {
		// TODO Auto-generated method stub
		 itemsDao.addNewItem(item);
		return item;
	}

	@Override
	public Item updateItem(Item item) {
		// TODO Auto-generated method stub
		itemsDao.updateItem(item);
		return item;
	}

	@Override
	public Item getItem(int id) {
		// TODO Auto-generated method stub
		Item item = itemsDao.getItemById(id);
		return item;
	}

	@Override
	public Order saveOrder(Order order) {
		// TODO Auto-generated method stub
		order.setOrderId(0);
		order.setOrderTime(System.currentTimeMillis());
		double orderAmount = 0;
		List<OrderItem> orderItems = order.getOrderItems();
		for(OrderItem oItem: orderItems) {
			oItem.setOrder(order);
			Item item = itemsDao.getItemById(oItem.getOrderItemId());
			if(item==null) {
				throw new ItemActionException("invalid item id. No item present in the database with that id");
			}
			int prevStock = item.getItemStock();
			int finalStock = prevStock - oItem.getOrderItemQuantity();
			if(finalStock<0) {
				throw new OrderActionException(item.getItemName()+" is Out of Stock or ivalid item quantity. Available: "+item.getItemStock());
			}
			item.setItemStock(prevStock-oItem.getOrderItemQuantity());
			itemsDao.updateItem(item);
			orderAmount = orderAmount + (item.getItemPrice()*oItem.getOrderItemQuantity());
			oItem.setOrderItemName(item.getItemName());
		}
		order.setOrderAmount(orderAmount);		
		order.setOrderItems(orderItems);
		
		orderDao.saveOrder(order);
		
		return order;
	}

	@Override
	public List<Order> getAllOrders(String userEmail) {
		// TODO Auto-generated method stub
		List<Order> orders = orderDao.getAllOrders(userEmail);
		return orders;
	}

	@Override
	public List<OrderItem> getOrderItems(String userEmail, int orderId) {
		// TODO Auto-generated method stub
		List<OrderItem> orderItems = orderDao.getOrderItems(userEmail, orderId);
		
		return orderItems;
	}

	@Override
	public Order getOrder(String userEmail, int orderId) {
		// TODO Auto-generated method stub
		Order order = orderDao.getOrder(userEmail, orderId);
		
		return order;
	}

	@Override
	public Cart addItemToCart(String userEmail, CartItem cartItem) {
		// TODO Auto-generated method stub
		Cart cart = cartDao.addItemToCart(userEmail, cartItem);
		return cart;
	}

	@Override
	public Cart deleteItemInCart(String userEmail, int itemId) {
		// TODO Auto-generated method stub
		Cart cart = cartDao.deleteItemInCart(userEmail, itemId, 0);
		return cart;
	}

	@Override
	public Cart getCart(String userEmail) {
		// TODO Auto-generated method stub
		Cart cart = cartDao.getCart(userEmail);
		return cart;
	}

	@Override
	public List<CartItem> getCartItems(String userEmail) {
		// TODO Auto-generated method stub
		List<CartItem> cartItems = cartDao.getCartItems(userEmail, 0);
		
		return cartItems;
	}

	@Override
	public Cart changeItemQuantityInCart(String userEmail, int itemId, int change) {
		// TODO Auto-generated method stub
		Cart cart = cartDao.changeCartItemQuantity(userEmail, itemId, change);
		return cart;
	}
	
	@Override
	public double cartAmount(String userEmail) {
		// TODO Auto-generated method stub
		Cart cart = getCart(userEmail);
		List<CartItem> cartItems = cart.getCartItems();
		double totalAmount =0;
		if(cartItems.size()==0) {
			return 0;
		}
		for(CartItem cartItem: cartItems ) {
			Item item = itemsDao.getItemById(cartItem.getItemId());
			if(item==null) {
				throw new ItemActionException("invalid item id. No item present in the database with that id");
			}
			totalAmount = totalAmount + (item.getItemPrice()*cartItem.getItemQuantity());
		}
		cartDao.cartAmount(totalAmount,userEmail);
		return totalAmount;
	}

	@Override
	public Cart createNewUserCart(String userEmail) {
		// TODO Auto-generated method stub
		Cart newCart = cartDao.createNewUserCart(userEmail);
		return newCart;
	}

}
