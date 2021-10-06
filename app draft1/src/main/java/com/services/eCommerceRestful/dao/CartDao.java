package com.services.eCommerceRestful.dao;

import java.util.List;

import com.services.eCommerceRestful.entity.Cart;
import com.services.eCommerceRestful.entity.CartItem;

public interface CartDao {
	
	public Cart getCart(String userEmail);
	
	public double cartAmount(double amount, String userEmail);
	
	public List<CartItem> getCartItems(String userEmail, int cartId);
	
	public Cart changeCartItemQuantity(String userEmail,int itemId, int change);
	
	public Cart deleteItemInCart(String userEmail , int itemId, int cartId);
	
	public Cart addItemToCart(String userEmail,CartItem cartItem);
	
	public Cart createNewUserCart(String userEmail);

}
