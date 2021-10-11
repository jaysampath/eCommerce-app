package com.services.eCommerceRestful.dao;

import java.util.List;

import com.services.eCommerceRestful.entity.Item;
import com.services.eCommerceRestful.entity.ItemReview;

public interface ItemsDao {
	
	public List<Item> getAllItems();
	
	public Item addNewItem(Item item);
	
	public Item getItemById(int id);
	
	public Item updateItem(Item item);
	
	public List<String> getAllCategories();
	
	public List<String> getSubCategoryNames(String itemCategory);
	
	public List<Item> getItemsBySubCategory(String itemCategory,String itemSubCategory);
	
	public ItemReview addItemReview(ItemReview itemReview);
	
	public List<ItemReview> getItemReviews(int itemId);

}
