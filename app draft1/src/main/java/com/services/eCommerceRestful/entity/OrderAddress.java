package com.services.eCommerceRestful.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="order_address")
public class OrderAddress {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="mobile")
	private String mobileNumber;
	
	@Column(name="address")
	private String address;
	
	@Column(name="city")
	private String city;
	
	@Column(name="state")
	private String state;
	
	@Column(name="pincode")
	private int pincode;
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	@JsonBackReference
	private Order order;
	
	public OrderAddress() {
		
	}
    
	public OrderAddress(String name, String mobileNumber, String address, String city, String state, int pincode,
			Order order) {
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.address = address;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
		this.order = order;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}


	@Override
	public String toString() {
		return "OrderAddress [id=" + id + ", name=" + name + ", mobileNumber=" + mobileNumber + ", address=" + address
				+ ", city=" + city + ", state=" + state + ", pincode=" + pincode + ", order=" + order + "]";
	}
    
	
}
