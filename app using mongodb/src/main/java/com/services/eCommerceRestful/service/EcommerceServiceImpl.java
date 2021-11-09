package com.services.eCommerceRestful.service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.services.eCommerceRestful.dao.CartDao;
import com.services.eCommerceRestful.dao.ItemsRepository;
import com.services.eCommerceRestful.dao.OrderDao;
import com.services.eCommerceRestful.dao.UserDao;
import com.services.eCommerceRestful.entity.Cart;
import com.services.eCommerceRestful.entity.CartItem;
import com.services.eCommerceRestful.entity.Item;
import com.services.eCommerceRestful.entity.ItemReview;
import com.services.eCommerceRestful.entity.Order;
import com.services.eCommerceRestful.entity.OrderItem;
import com.services.eCommerceRestful.entity.User;
import com.services.eCommerceRestful.helpers.EmailActionException;
import com.services.eCommerceRestful.helpers.ItemActionException;
import com.services.eCommerceRestful.helpers.LoginInput;
import com.services.eCommerceRestful.helpers.OrderActionException;
import com.services.eCommerceRestful.helpers.UserActionException;

@Service
@Transactional
public class EcommerceServiceImpl implements EcommerceService {
	
	@Autowired
	private UserDao usersDao;

	@Autowired
	private ItemsRepository itemsRepository;

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private CartDao cartDao;
	
	@Autowired
	private EmailService emailService;

	SimpleDateFormat sdf = new SimpleDateFormat();

	Logger logger = LoggerFactory.getLogger(EcommerceService.class);
	
	@Resource(name="state-code-map")
    private Map<String,String> stateCodes;
	
	
	@Override
	public User saveNewUser(User user) {
		// TODO Auto-generated method stub
		boolean checkIfExisting = usersDao.checkUserExists(user.getUserEmail());
		if(checkIfExisting) {
			throw new UserActionException("user already exists");
		}
		User newUser = usersDao.newUserRegister(user);
		try {
			emailService.sendSuccessfulSignupEmail(user.getUserEmail());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			throw new EmailActionException(e.getMessage());
		}
		return newUser;
	}
	
	@Override
	public User getUserByEmail(String userEmail) {
		// TODO Auto-generated method stub
		User user = usersDao.getUserByEmail(userEmail);
		if(user==null) {
			throw new UserActionException("user not found");
		}
		return user;
	}
	
	@Override
	public String checkUserIsAuth(LoginInput loginInput) {
		// TODO Auto-generated method stub
		String response = usersDao.isUserAuthenticated(loginInput);
		if(response.equals("no")) {
			throw new UserActionException("The email and password combination is incorrect");
			
		}
		return "yes";
	}
	
	@Override
	public String checkExistingUser(String email) {
		// TODO Auto-generated method stub
		boolean check = usersDao.checkUserExists(email);
		if(check) {
			throw new UserActionException("user already exists. please login with the same.");
		}
		return "no";
	}
	
	@Override
	public User updateUserPassword(String email, String password) {
		// TODO Auto-generated method stub
		User user = usersDao.updateUserPassword(email, password);
		if(user==null) {
			throw new UserActionException("invalid user email");
		}
		return user;
	}



	@Override
	public List<Item> getAllItems() {
		// TODO Auto-generated method stub
		// logger.info("inside getAllItems method with current
		// thread-{}",Thread.currentThread().getName());
		List<Item> items = itemsRepository.getAllItems();
		return items;
	}

	@Override
	@Async
	public Item addNewItem(Item item) {
		// TODO Auto-generated method stub
		// logger.info("inside addNewItem method with current
		// thread-{}",Thread.currentThread().getName());
		Item newOrUpdatedItem = itemsRepository.addNewItem(item);
		return newOrUpdatedItem;
	}

	@Override
	public Item updateItem(Item item) {
		// TODO Auto-generated method stub
		// logger.info("inside updateItem method with current
		// thread-{}",Thread.currentThread().getName());
		Item updatedItem = itemsRepository.updateItem(item);
		return updatedItem;
	}

	@Override
	public Item getItem(int id) {
		// TODO Auto-generated method stub
		// logger.info("inside getItem method with current
		// thread-{}",Thread.currentThread().getName());
		Item item = itemsRepository.getItemById(id);
		if (item == null) {
			throw new ItemActionException("Item with id-" + id + " not found");
		}
		return item;
	}

	@Override
	public List<String> getAllCategories() {
		// TODO Auto-generated method stub
		// logger.info("inside getAllCategories method with current
		// thread-{}",Thread.currentThread().getName());
		List<String> categoryNames = itemsRepository.getItemCategoryNames();
		return categoryNames;
	}

	@Override
	public List<String> getSubCategoryNames(String itemCategory) {
		// TODO Auto-generated method stub
		// logger.info("inside getSubCategoryNames method with current
		// thread-{}",Thread.currentThread().getName());
		List<String> subCategoryNames = itemsRepository.getItemSubCategoryNames(itemCategory);
		return subCategoryNames;
	}

	@Override
	@Async
	public List<Item> getItemsBySubCategory(String itemCategory, String itemSubCategory) {
		// TODO Auto-generated method stub
		// logger.info("inside getItemsBySubCategory method with current
		// thread-{}",Thread.currentThread().getName());
		List<Item> items = itemsRepository.getItemsBySubCategory(itemCategory, itemSubCategory);
		return items;
	}

	@Override
	@Async
	public ItemReview addItemReview(ItemReview itemReview) {
		// TODO Auto-generated method stub
		
		// thread-{}",Thread.currentThread().getName());
		ItemReview insertedItemReview = itemsRepository.addItemReview(itemReview);
		 //logger.info("inside addItemReview method with current"+insertedItemReview.toString());
		return insertedItemReview;
	}

	@Override
	@Async
	public List<ItemReview> getItemReviews(long itemId) {
		// TODO Auto-generated method stub
		// logger.info("inside getItemReviews method with current
		// thread-{}",Thread.currentThread().getName());
		List<ItemReview> itemReviews = itemsRepository.getItemReviews(itemId);
		return itemReviews;
	}
	
	@Override
	public List<ItemReview> getItemTopReviews(long itemId) {
		// TODO Auto-generated method stub
		List<ItemReview> topReviews = itemsRepository.getItemTopReviews(itemId);
		return topReviews;
	}
	
	@Override
	public List<Item> getItemsBySearch(String queryString) {
		// TODO Auto-generated method stub
		List<Item> itemsBySearch = itemsRepository.getItemsBySearch(queryString);
		return itemsBySearch;
	}


	@Override
	@Async
	public Order saveOrder(Order order) {
		// TODO Auto-generated method stub
		logger.info("inside saveOrder method with current thread-> {}", Thread.currentThread().getName());
		boolean userCheck = usersDao.checkUserExists(order.getUserEmail());
		if(!userCheck) {
			throw new UserActionException("user email invalid. please try again");
		}
		String addressState = order.getOrderAddress().getState();
		String stateCode = stateCodes.get(addressState);
		String orderSequenceName = Order.SEQUENCE_NAME;
		String tempOrderId= "";
		if(stateCode!=null) {
			orderSequenceName = orderSequenceName+"_"+ stateCode;
			tempOrderId = stateCode+"_"+(SequenceGeneratorService.generateSequence(orderSequenceName));
		}else {
			tempOrderId = "default_"+(SequenceGeneratorService.generateSequence(orderSequenceName));
		}
		//logger.info(orderSequenceName);
		order.setOrderId(tempOrderId);
		order.setOrderTime(String.valueOf(sdf.format(System.currentTimeMillis())));
		double orderAmount = 0;
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem oItem : orderItems) {
			Item item = itemsRepository.getItemById(oItem.getOrderItemId());
			if (item == null) {
				throw new ItemActionException("invalid item id. No item present in the database with that id");
			}
			int prevStock = item.getItemStock();
			int finalStock = prevStock - oItem.getOrderItemQuantity();
			if (finalStock < 0) {
				throw new OrderActionException(item.getItemName()
						+ " is Out of Stock or ivalid item quantity. Available: " + item.getItemStock());
			}
			item.setItemStock(prevStock - oItem.getOrderItemQuantity());
			itemsRepository.updateItem(item);
			orderAmount = orderAmount + (item.getItemPrice() * oItem.getOrderItemQuantity());
			oItem.setOrderItemName(item.getItemName());
			oItem.setOrderItemImageUrl(item.getItemImageUrl());
			oItem.setOrderItemPrice(item.getItemPrice());
			
		}
		order.setOrderAmount(orderAmount);
		order.setOrderItems(orderItems);
		orderDao.saveOrder(order);
        try {
			emailService.sendOrderPlacedMail(order);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			throw new EmailActionException(e.getMessage()); 
		}
		return order;
	}

	@Override
	public List<Order> getAllOrders(String userEmail) {
		// TODO Auto-generated method stub
		// logger.info("inside getAllOrders method with current
		// thread-{}",Thread.currentThread().getName());
		List<Order> orders = orderDao.getAllOrders(userEmail);
		//logger.info(stateCodes.get("Andhra Pradesh"));
		return orders;
	}

	@Override
	public List<OrderItem> getOrderItems(String userEmail, int orderId) {
		// TODO Auto-generated method stub
		// logger.info("inside getOrderItems method with current
		// thread-{}",Thread.currentThread().getName());
		List<OrderItem> orderItems = orderDao.getOrderItems(userEmail, orderId);
		return orderItems;
	}

	@Override
	public Order getOrder(String userEmail, int orderId) {
		// TODO Auto-generated method stub
		// logger.info("inside getOrder method with current
		// thread-{}",Thread.currentThread().getName());
		Order order = orderDao.getOrder(userEmail, orderId);
		return order;
	}

	@Override
	public Cart addItemToCart(String userEmail, CartItem cartItem) {
		// TODO Auto-generated method stub
		//logger.info("inside addItemToCart method with current thread-{}",Thread.currentThread().getName());
		Item item = itemsRepository.getItemById(cartItem.getItemId());
		if(item!=null) {
			cartItem.setItemImageUrl(item.getItemImageUrl());
			cartItem.setItemManufacturer(item.getItemManufacturer());
			cartItem.setItemStock(item.getItemStock());
			cartItem.setItemName(item.getItemName());
			cartItem.setItemPrice(item.getItemPrice());
		}
		Cart cart = cartDao.addItemToCart(userEmail, cartItem);
		return cart;
	}

	@Override
	@Async
	public Cart deleteItemInCart(String userEmail, int itemId) {
		// TODO Auto-generated method stub
		//logger.info("inside deleteItemInCart method with current thread-{}",Thread.currentThread().getName());
		Cart cart = cartDao.deleteItemInCart(userEmail, itemId, 0);
		return cart;
	}

	@Override
	public Cart getCart(String userEmail) {
		// TODO Auto-generated method stub
		//logger.info("inside getCart method with current thread-{}",Thread.currentThread().getName());
		Cart cart = cartDao.getCart(userEmail);
		return cart;
	}

	@Override
	public List<CartItem> getCartItems(String userEmail) {
		// TODO Auto-generated method stub
		//logger.info("inside getCartItems method with current thread-{}",Thread.currentThread().getName());
		List<CartItem> cartItems = cartDao.getCartItems(userEmail, 0);
		
		return cartItems;
	}

	@Override
	public Cart changeItemQuantityInCart(String userEmail, int itemId, int change) {
		// TODO Auto-generated method stub
		//logger.info("inside changeItemQuantityInCart method with current thread-{}",Thread.currentThread().getName());
		Cart cart = cartDao.changeCartItemQuantity(userEmail, itemId, change);
		return cart;
	}
	
	@Override
	public double cartAmount(String userEmail) {
		// TODO Auto-generated method stub
		//logger.info("inside cartAmount method with current thread-{}",Thread.currentThread().getName());
		Cart cart = getCart(userEmail);
		List<CartItem> cartItems = cart.getCartItems();
		double totalAmount =0;
		if(cartItems.size()==0) {
			cartDao.cartAmount(totalAmount,userEmail);
			return 0;
		}
		for(CartItem cartItem: cartItems ) { 
			Item item = itemsRepository.getItemById(cartItem.getItemId());
			if(item==null) {
				throw new ItemActionException("invalid item id. No item present in the database with that id");
			}
			totalAmount = totalAmount + (item.getItemPrice()*cartItem.getItemQuantity());
		}
		//logger.info("totalAmount in service: {}",totalAmount);
		cartDao.cartAmount(totalAmount,userEmail);
		return totalAmount;
	}

	@Override
	public Cart createNewUserCart(String userEmail) {
		// TODO Auto-generated method stub
		//logger.info("inside createNewUserCart method with current thread-{}",Thread.currentThread().getName());
		Cart newCart = cartDao.createNewUserCart(userEmail);
		return newCart;
	}

	

	
}
