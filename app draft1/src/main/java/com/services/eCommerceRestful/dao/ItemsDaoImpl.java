package com.services.eCommerceRestful.dao;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
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
		Query query = session.createQuery("from Item");
		List<Item> items = query.getResultList();
		return items;
	}

	@Override
	public Item addNewItem(Item item) {
		// TODO Auto-generated method stub
		
		Session session = entityManager.unwrap(Session.class);
		session.save(item);
		
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

}
