package com.services.eCommerceRestful.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="cart")
public class Cart {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cart_id")
	private int cartId;
	
	@Column(name="user_email")
	private String userEmail;
	
	@Column(name="cart_amount")
	private double cartAmount;
	
	@OneToMany(mappedBy="cart",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JsonManagedReference
	private List<CartItem> cartItems;
	
	public Cart() {
		
	}

	public Cart(String userEmail, List<CartItem> cartItems, double cartAmount) {
		this.userEmail = userEmail;
		this.cartAmount = cartAmount;
		this.cartItems = cartItems;
	}

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public double getCartAmount() {
		return cartAmount;
	}

	public void setCartAmount(double cartAmount) {
		this.cartAmount = cartAmount;
	}

	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", userEmail=" + userEmail + ", cartAmount=" + cartAmount + ", cartItems="
				+ cartItems + "]";
	}

	
	
	

}
