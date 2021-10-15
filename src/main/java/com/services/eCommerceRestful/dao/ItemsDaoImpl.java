package com.services.eCommerceRestful.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import javax.persistence.EntityManager;


import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.services.eCommerceRestful.entity.Item;
import com.services.eCommerceRestful.entity.ItemReview;

@Repository
public class ItemsDaoImpl implements ItemsDao {
	
	private EntityManager entityManager;
	
	public ItemsDaoImpl(EntityManager entityManager) {
		this.entityManager=entityManager;
	}

	@Override
	public List<Item> getAllItems() {
		// TODO Auto-generated method stub
		Session session = entityManager.unwrap(Session.class);
		Query<Item> query = session.createQuery("from Item");
		List<Item> items = query.getResultList();
		return items;
	}

	@Override
	public Item addNewItem(Item item) {
		// TODO Auto-generated method stub
		
		Session session = entityManager.unwrap(Session.class);
		Query<Item> query = session.createQuery("from Item where itemName=:name and itemManufacturer=:company");
		query.setParameter("name", item.getItemName());
		query.setParameter("company", item.getItemManufacturer());
		List<Item> items = query.getResultList();
		session.clear();
		if(items.size()==0) {
			session.save(item);
		}else if(items.size()==1) {
			int prevStock = items.get(0).getItemStock();
			int prevId = items.get(0).getItemId();
			item.setItemId(prevId);
			int finalStock = item.getItemStock()+prevStock;
			item.setItemStock(finalStock);
			session.update("Item", item);
		}
		
		
		return item;
	}

	@Override
	public Item getItemById(int id) {
		// TODO Auto-generated method stub
		Session session = entityManager.unwrap(Session.class);
		
		Item item = session.get(Item.class, id);
		
		return item;
	}

	@Override
	public Item updateItem(Item item) {
		// TODO Auto-generated method stub
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(item);
		return item;
	}

	@Override
	public List<String> getAllCategories() {
		// TODO Auto-generated method stub
		Session session = entityManager.unwrap(Session.class);
		Query<String> query = session.createQuery("select distinct(itemCategory) from Item");
		List<String> names = query.getResultList();
		return names;
	}

	@Override
	public List<String> getSubCategoryNames(String itemCategory) {
		// TODO Auto-generated method stub
		Session session = entityManager.unwrap(Session.class);
		Query<String> query = session.createQuery("select distinct(itemSubCategory) from Item where itemCategory=:categoryName ");
		query.setParameter("categoryName", itemCategory);
		List<String> names = query.getResultList();
		return names;
	}

	@Override
	public List<Item> getItemsBySubCategory(String itemCategory,String itemSubCategory) {
		// TODO Auto-generated method stub
		Session session = entityManager.unwrap(Session.class);
		Query<Item> query = session.createQuery("from Item where itemCategory=:categoryName and itemSubCategory=:subCategoryName");
		query.setParameter("subCategoryName", itemSubCategory);
		query.setParameter("categoryName", itemCategory);
		List<Item> items = query.getResultList();
		return items;
	}
	
   public void updateItemRatings(int itemId, float rating) {
	   Session session = entityManager.unwrap(Session.class);
	   Item item = session.get(Item.class, itemId);
	   float prevRating = item.getItemRating();
	   int prevNumRatings = item.getNumRatings();
	   float newRating = 0;
	   newRating = (((prevRating*prevNumRatings) + rating ) / (prevNumRatings+1));
	   newRating = (float) Math.round(newRating * 100) / 100;
	   item.setItemRating(newRating);
	   item.setNumRatings(prevNumRatings+1);
	   session.update("item", item);
   }

	@Override
	public ItemReview addItemReview(ItemReview itemReview) {
		// TODO Auto-generated method stub
		Session session = entityManager.unwrap(Session.class);
		SimpleDateFormat sdf = new SimpleDateFormat();
		String time =String.valueOf(sdf.format(System.currentTimeMillis()));
		itemReview.setPostedTime(time);
		session.save(itemReview);
		updateItemRatings(itemReview.getItemId(),itemReview.getRating());
		return itemReview;
	}

	@Override
	public List<ItemReview> getItemReviews(int itemId) {
		// TODO Auto-generated method stub
		Session session = entityManager.unwrap(Session.class);
		Query<ItemReview> query = session.createQuery("from ItemReview where itemId=:itemId ");
		query.setParameter("itemId", itemId);
		List<ItemReview> itemReviews = query.getResultList();
		return itemReviews;
	}


}
