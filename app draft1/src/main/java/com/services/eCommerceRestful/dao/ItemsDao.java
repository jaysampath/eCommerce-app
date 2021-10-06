package com.services.eCommerceRestful.dao;

import java.util.List;

import com.services.eCommerceRestful.entity.Item;

public interface ItemsDao {
	
	public List<Item> getAllItems();
	
	public Item addNewItem(Item item);
	
	public Item getItemById(int id);
	
	public Item updateItem(Item item);

}
