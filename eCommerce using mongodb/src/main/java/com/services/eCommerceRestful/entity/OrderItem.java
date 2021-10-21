package com.services.eCommerceRestful.entity;

public class OrderItem {

	private int orderItemId;

	private String orderItemName;

	private int orderItemQuantity;

	public OrderItem() {

	}

	public OrderItem(int orderItemId, String orderItemName, int orderItemQuantity) {
		this.orderItemId = orderItemId;
		this.orderItemName = orderItemName;
		this.orderItemQuantity = orderItemQuantity;
	}

	public int getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getOrderItemName() {
		return orderItemName;
	}

	public void setOrderItemName(String orderItemName) {
		this.orderItemName = orderItemName;
	}

	public int getOrderItemQuantity() {
		return orderItemQuantity;
	}

	public void setOrderItemQuantity(int orderItemQuantity) {
		this.orderItemQuantity = orderItemQuantity;
	}

}
