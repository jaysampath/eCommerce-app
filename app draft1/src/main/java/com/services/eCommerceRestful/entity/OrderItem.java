package com.services.eCommerceRestful.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="order_item")
public class OrderItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@Column(name="order_item_id")
	private int orderItemId;
	
	@Column(name="order_item_name")
	private String orderItemName;
	
	@Column(name="order_item_quantity")
	private int orderItemQuantity; 
	
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	@JsonBackReference
	private Order order;
	
	public OrderItem() {
		
	}

	public OrderItem(int orderItemId, String orderItemName, int orderItemQuantity, Order order) {
		super();
		this.orderItemId = orderItemId;
		this.orderItemName = orderItemName;
		this.orderItemQuantity = orderItemQuantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", orderItemId=" + orderItemId + ", orderItemName=" + orderItemName
				+ ", orderItemQuantity=" + orderItemQuantity + ", order=" + order + "]";
	}

	
	
	
	
	

}
