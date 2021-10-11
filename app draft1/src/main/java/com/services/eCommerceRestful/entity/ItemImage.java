package com.services.eCommerceRestful.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="item_image")
public class ItemImage {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="url")
	private String url;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="item_id")
	@JsonBackReference
	private Item item;
	
	public ItemImage() {
		
	}

	public ItemImage(String url, Item item) {
		this.url = url;
		this.item = item;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "ItemImage [id=" + id + ", url=" + url + ", item=" + item + "]";
	}
	
	
	

}
