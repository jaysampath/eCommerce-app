package com.services.eCommerceRestful.dao;

import java.util.List;


import javax.persistence.EntityManager;


import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.services.eCommerceRestful.entity.Item;

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

}
