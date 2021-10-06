package com.services.eCommerceRestful.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.services.eCommerceRestful.entity.Cart;
import com.services.eCommerceRestful.entity.CartItem;

@Repository
public class CartDaoImpl implements CartDao {
	
	private EntityManager entityManager;
	
	 
	LogManager slg = LogManager.getLogManager();        
    Logger log = slg.getLogger(Logger.GLOBAL_LOGGER_NAME);   
	
	public CartDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	

	@Override
	public Cart getCart(String userEmail) {
		// TODO Auto-generated method stub
		
		Session session = entityManager.unwrap(Session.class);
	    Query<Cart> query = session.createQuery("from Cart where userEmail=:email");
	    query.setParameter("email", userEmail);
	    //Cart cart = query.getSingleResult();
	    List<Cart> cartCheck = query.getResultList();
	    if(cartCheck.size()==0) {
	    	Cart newCart = createNewUserCart(userEmail);
	    	return newCart;
	    }
	    
		return cartCheck.get(0);
	}

	

	@Override
	public List<CartItem> getCartItems(String userEmail, int cartId) {
		// TODO Auto-generated method stub
		Cart cart = getCart(userEmail);
		List<CartItem> cartItems = cart.getCartItems();
		return cartItems;
	}

	@Override
	public Cart changeCartItemQuantity(String userEmail,int itemId, int change) {
		// TODO Auto-generated method stub
		Session session = entityManager.unwrap(Session.class);
		Cart cart = getCart(userEmail);
		List<CartItem> cartItems = cart.getCartItems();
		List<CartItem> newCartItems = new ArrayList<>();
		for(CartItem cartItem: cartItems) {
			if(cartItem.getItemId()==itemId) {
				if(change==-1 && cartItem.getItemQuantity()<=1) {
					deleteItemInCart(userEmail,cartItem.getItemId(),cart.getCartId());
				}else {
					int prevItemQuantity = cartItem.getItemQuantity();
					cartItem.setItemQuantity( prevItemQuantity + change );
					newCartItems.add(cartItem);
				}
				
			}else {
				newCartItems.add(cartItem);
			}
			
		}
		cart.setCartItems(newCartItems);
		return cart;
	}

	@Override
	public Cart deleteItemInCart(String userEmail, int itemId, int cartId) {
		// TODO Auto-generated method stub
		Cart cart = getCart(userEmail);
		List<CartItem> cartItems = cart.getCartItems();
		//System.out.println(cartItems);
		List<CartItem> newCartItems = new ArrayList<>();
		for(CartItem item: cartItems) {
			if(item.getItemId()!=itemId) {
				newCartItems.add(item);
			}
		}
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("delete from CartItem where cart_id=:cartId and item_id=:itemId");
		query.setParameter("cartId", cart.getCartId());
		query.setParameter("itemId", itemId);
		query.executeUpdate();
		cart.setCartItems(newCartItems);
	
		return cart;
	}

	@Override
	public Cart addItemToCart(String userEmail,CartItem cartItem) {
		// TODO Auto-generated method stub
		Session session = entityManager.unwrap(Session.class);
		Cart cart = getCart(userEmail);
	     
	     Query query = session.createQuery("from CartItem where cart_id=:id and itemId=:itemId");
	     query.setParameter("id", cart.getCartId());
	     query.setParameter("itemId", cartItem.getItemId());
	     List<CartItem> existed = query.getResultList();
	     session.clear();
	     
	     if(existed.size()==0) {
	    	 cartItem.setCart(cart);
	         session.save(cartItem);
	     }else if(existed.size()==1) {
	    	 int initialQuantity = existed.get(0).getItemQuantity();
	    	 int finalQuantity = initialQuantity + cartItem.getItemQuantity();
	    	 query = session.createQuery("update CartItem set itemQuantity =: newQuantity where id=:id");
	    	 query.setParameter("newQuantity", finalQuantity );
	    	 query.setParameter("id", existed.get(0).getId() );
	    	 query.executeUpdate();  
	     }
	     
	     Cart newCart = getCart(userEmail);
		return newCart;
	}

	@Override
	public double cartAmount(double totalAmount, String userEmail) {
		// TODO Auto-generated method stub
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("update Cart set cart_amount=:amount where userEmail=:email ");
		query.setParameter("email", userEmail);
		query.setParameter("amount", totalAmount);
		query.executeUpdate();
		
		return totalAmount;
	}


	@Override
	public Cart createNewUserCart(String userEmail) {
		// TODO Auto-generated method stub
		Session session = entityManager.unwrap(Session.class);
		List<CartItem> tempCartItems = new ArrayList<>();
		Cart cart = new Cart(userEmail,tempCartItems,0);
		session.save(cart);
	   
		return cart;
	}

	


}
