package com.services.eCommerceRestful.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.services.eCommerceRestful.entity.Cart;
import com.services.eCommerceRestful.entity.CartItem;
import com.services.eCommerceRestful.entity.Item;
import com.services.eCommerceRestful.entity.ItemReview;
import com.services.eCommerceRestful.entity.Order;
import com.services.eCommerceRestful.entity.OrderItem;

public interface EcommerceService {
	
	public List<Item> getAllItems();
	
	public Item addNewItem(Item item);
	
	public Item updateItem(Item item);
	
	public Item getItem(int id);
	
	public List<String> getAllCategories();
	
	public List<String> getSubCategoryNames(String itemCategory);
	
	public List<Item> getItemsBySubCategory(String itemCategory,String itemSubCategory);
	
	public ItemReview addItemReview(ItemReview itemReview);
	
	public List<ItemReview> getItemReviews(int itemId);
	
	public CompletableFuture<Order> saveOrder(Order order) ;
	
	public List<Order> getAllOrders(String userEmail);
	
    public List<OrderItem> getOrderItems(String userEmail,int orderId);
	
	public Order getOrder(String userEmail, int orderId);
	
	public Cart addItemToCart(String userEmail,CartItem cartItem);
	
	public Cart deleteItemInCart(String userEmail,int itemId);
	
	public Cart getCart(String userEmail);
	
	public List<CartItem> getCartItems(String userEmail);
	
	public Cart changeItemQuantityInCart(String userEmail,int itemId, int change);

	double cartAmount(String userEmail);
	
	public Cart createNewUserCart(String userEmail) ;

}
