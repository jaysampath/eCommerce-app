package com.services.eCommerceRestful.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="item_review")
public class ItemReview {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="user_email")
	private String userEmail;
	
	@Column(name="review")
	private String review;
	
	@Column(name="date")
	private String postedTime;
	
	@Column(name="rating")
	private float rating;
	
	@Column(name="item_id")
	private int itemId;
	
	public ItemReview() {
		
	}

	public ItemReview(String userEmail, String review, String postedTime, float rating, int itemId) {
		this.userEmail = userEmail;
		this.review = review;
		this.postedTime = postedTime;
		this.rating = rating;
		this.itemId = itemId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getPostedTime() {
		return postedTime;
	}

	public void setPostedTime(String postedTime) {
		this.postedTime = postedTime;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	@Override
	public String toString() {
		return "ItemReview [id=" + id + ", userEmail=" + userEmail + ", review=" + review + ", postedTime=" + postedTime
				+ ", rating=" + rating + ", itemId=" + itemId + "]";
	}

	

	
	
	

}
