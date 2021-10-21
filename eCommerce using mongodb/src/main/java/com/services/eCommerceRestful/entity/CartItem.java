package com.services.eCommerceRestful.entity;


public class CartItem {
    
	private long itemId;
	
	
	private int itemQuantity;
	
	public CartItem() {
		
	}

	public CartItem(long itemId, int itemQuantity) {
		this.itemId = itemId;
		this.itemQuantity = itemQuantity;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public int getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	
	
}
