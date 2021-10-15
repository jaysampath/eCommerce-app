package com.services.eCommerceRestful.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="item")
public class Item {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="item_id")
	private int itemId;
	
	
	@Column(name="item_name",nullable=false)
	private String itemName;
	
	
	@Column(name="item_price",nullable=false)
	private int itemPrice;
	
	@Column(name="item_category")
	private String itemCategory;
	
	@Column(name="item_subCategory")
	private String itemSubCategory;
	
	@Column(name="item_manufacturer")
	private String itemManufacturer;
	
	@Column(name="item_stock")
	private int itemStock;
	
	@Column(name="item_rating",columnDefinition="integer default 0")
	private Float itemRating;
	
	@Column(name="num_ratings",columnDefinition="integer default 0")
	private int numRatings;
	
	@OneToOne(mappedBy="item",cascade=CascadeType.ALL)
	@JsonManagedReference
	private ItemImage itemImage;
		
	public Item() {
		
	}

	public Item(String itemName, int itemPrice, String itemCategory, String itemSubCategory, String itemManufacturer,
			int itemStock, Float itemRating,int numRatings, ItemImage itemImage ) {
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.itemCategory = itemCategory;
		this.itemSubCategory = itemSubCategory;
		this.itemManufacturer = itemManufacturer;
		this.itemStock = itemStock;
		this.itemRating = itemRating;
		this.numRatings=numRatings;
		this.itemImage=itemImage;
	}
	
	

	public ItemImage getItemImage() {
		return itemImage;
	}

	public void setItemImage(ItemImage itemImage) {
		this.itemImage = itemImage;
	}

	public Float getItemRating() {
		return itemRating;
	}

	public void setItemRating(Float itemRating) {
		this.itemRating = itemRating;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getItemSubCategory() {
		return itemSubCategory;
	}

	public void setItemSubCategory(String itemSubCategory) {
		this.itemSubCategory = itemSubCategory;
	}

	public String getItemManufacturer() {
		return itemManufacturer;
	}

	public void setItemManufacturer(String itemManufacturer) {
		this.itemManufacturer = itemManufacturer;
	}

	public int getItemStock() {
		return itemStock;
	}

	public void setItemStock(int itemStock) {
		this.itemStock = itemStock;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}

	public int getNumRatings() {
		return numRatings;
	}

	public void setNumRatings(int numRatings) {
		this.numRatings = numRatings;
	}

	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", itemName=" + itemName + ", itemPrice=" + itemPrice + ", itemCategory="
				+ itemCategory + ", itemSubCategory=" + itemSubCategory + ", itemManufacturer=" + itemManufacturer
				+ ", itemStock=" + itemStock + ", itemRating=" + itemRating + ", numRatings=" + numRatings + "]";
	}

	
	
    
	

	

}
