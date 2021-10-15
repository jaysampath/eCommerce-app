package com.services.eCommerceRestful.service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.services.eCommerceRestful.dao.CartDao;
import com.services.eCommerceRestful.dao.ItemsDao;
import com.services.eCommerceRestful.dao.OrderDao;
import com.services.eCommerceRestful.entity.Cart;
import com.services.eCommerceRestful.entity.CartItem;
import com.services.eCommerceRestful.entity.Item;
import com.services.eCommerceRestful.entity.ItemReview;
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
	
	SimpleDateFormat sdf = new SimpleDateFormat();
	
	Logger logger = LoggerFactory.getLogger(EcommerceService.class);

	@Override
	public List<Item> getAllItems() {
		// TODO Auto-generated method stub
		//logger.info("inside getAllItems method with current thread-{}",Thread.currentThread().getName());
		List<Item> items = itemsDao.getAllItems();
		return items;
	}

	@Override
	@Async
	public Item addNewItem(Item item) {
		// TODO Auto-generated method stub
		//logger.info("inside addNewItem method with current thread-{}",Thread.currentThread().getName());
		 itemsDao.addNewItem(item);
		return item;
	}

	@Override
	public Item updateItem(Item item) {
		// TODO Auto-generated method stub
		//logger.info("inside updateItem method with current thread-{}",Thread.currentThread().getName());
		itemsDao.updateItem(item);
		return item;
	}

	@Override
	public Item getItem(int id) {
		// TODO Auto-generated method stub
		//logger.info("inside getItem method with current thread-{}",Thread.currentThread().getName());
		Item item = itemsDao.getItemById(id);
		return item;
	}
	
	@Override
	public List<String> getAllCategories() {
		// TODO Auto-generated method stub
		//logger.info("inside getAllCategories method with current thread-{}",Thread.currentThread().getName());
		List<String> names = itemsDao.getAllCategories();
		return names;
	}

	@Override
	public List<String> getSubCategoryNames(String itemCategory) {
		// TODO Auto-generated method stub
		//logger.info("inside getSubCategoryNames method with current thread-{}",Thread.currentThread().getName());
		List<String> names = itemsDao.getSubCategoryNames(itemCategory);
		return names;
	}

	@Override
	@Async
	public List<Item> getItemsBySubCategory(String itemCategory,String itemSubCategory) {
		// TODO Auto-generated method stub
		//logger.info("inside getItemsBySubCategory method with current thread-{}",Thread.currentThread().getName());
		List<Item> items = itemsDao.getItemsBySubCategory(itemCategory, itemSubCategory);
		return items;
	}
	
	@Override
	@Async
	public ItemReview addItemReview(ItemReview itemReview) {
		// TODO Auto-generated method stub
		//logger.info("inside addItemReview method with current thread-{}",Thread.currentThread().getName());
		ItemReview review = itemsDao.addItemReview(itemReview);
		return review;
	}
	
	@Override
	@Async
	public List<ItemReview> getItemReviews(int itemId) {
		// TODO Auto-generated method stub
		//logger.info("inside getItemReviews method with current thread-{}",Thread.currentThread().getName());
		List<ItemReview> itemReviews = itemsDao.getItemReviews(itemId);
		return itemReviews;
	}


	@Override
	@Async
	public CompletableFuture<Order> saveOrder(Order order) {
		// TODO Auto-generated method stub
		logger.info("inside saveOrder method with current thread-> {}",Thread.currentThread().getName());
		order.setOrderId(0);
		order.setOrderTime(String.valueOf(sdf.format(System.currentTimeMillis())));
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
		
		return CompletableFuture.completedFuture(order);
	}
 
	@Override
	public List<Order> getAllOrders(String userEmail) {
		// TODO Auto-generated method stub
		//logger.info("inside getAllOrders method with current thread-{}",Thread.currentThread().getName());
		List<Order> orders = orderDao.getAllOrders(userEmail);
		return orders;
	}

	@Override
	public List<OrderItem> getOrderItems(String userEmail, int orderId) {
		// TODO Auto-generated method stub
		//logger.info("inside getOrderItems method with current thread-{}",Thread.currentThread().getName());
		List<OrderItem> orderItems = orderDao.getOrderItems(userEmail, orderId);
		
		return orderItems;
	}

	@Override
	public Order getOrder(String userEmail, int orderId) {
		// TODO Auto-generated method stub
		//logger.info("inside getOrder method with current thread-{}",Thread.currentThread().getName());
		Order order = orderDao.getOrder(userEmail, orderId);
		
		return order;
	}

	@Override
	public Cart addItemToCart(String userEmail, CartItem cartItem) {
		// TODO Auto-generated method stub
		//logger.info("inside addItemToCart method with current thread-{}",Thread.currentThread().getName());
		Cart cart = cartDao.addItemToCart(userEmail, cartItem);
		return cart;
	}

	@Override
	@Async
	public Cart deleteItemInCart(String userEmail, int itemId) {
		// TODO Auto-generated method stub
		//logger.info("inside deleteItemInCart method with current thread-{}",Thread.currentThread().getName());
		Cart cart = cartDao.deleteItemInCart(userEmail, itemId, 0);
		return cart;
	}

	@Override
	public Cart getCart(String userEmail) {
		// TODO Auto-generated method stub
		//logger.info("inside getCart method with current thread-{}",Thread.currentThread().getName());
		Cart cart = cartDao.getCart(userEmail);
		return cart;
	}

	@Override
	public List<CartItem> getCartItems(String userEmail) {
		// TODO Auto-generated method stub
		//logger.info("inside getCartItems method with current thread-{}",Thread.currentThread().getName());
		List<CartItem> cartItems = cartDao.getCartItems(userEmail, 0);
		
		return cartItems;
	}

	@Override
	public Cart changeItemQuantityInCart(String userEmail, int itemId, int change) {
		// TODO Auto-generated method stub
		//logger.info("inside changeItemQuantityInCart method with current thread-{}",Thread.currentThread().getName());
		Cart cart = cartDao.changeCartItemQuantity(userEmail, itemId, change);
		return cart;
	}
	
	@Override
	public double cartAmount(String userEmail) {
		// TODO Auto-generated method stub
		//logger.info("inside cartAmount method with current thread-{}",Thread.currentThread().getName());
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
		//logger.info("inside createNewUserCart method with current thread-{}",Thread.currentThread().getName());
		Cart newCart = cartDao.createNewUserCart(userEmail);
		return newCart;
	}

	

	
	

}
