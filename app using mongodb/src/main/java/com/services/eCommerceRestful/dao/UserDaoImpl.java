package com.services.eCommerceRestful.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.services.eCommerceRestful.entity.User;
import com.services.eCommerceRestful.helpers.LoginInput;
import com.services.eCommerceRestful.helpers.UserActionException;
import com.services.eCommerceRestful.service.SequenceGeneratorService;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	@Override
	public User newUserRegister(User user) {
		// TODO Auto-generated method stub
		user.setUserId(SequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(hashedPassword);
		mongoTemplate.save(user);
		return user;
	}

	@Override
	public boolean checkUserExists(String userEmail) {
		// TODO Auto-generated method stub
		Query query = new Query();
		query.addCriteria(Criteria.where("userEmail").is(userEmail));
		List<User> users = mongoTemplate.find(query, User.class, "user");
		if(users.size()==1) {
			return true;
		}
		return false;
	}

	@Override
	public String isUserAuthenticated(LoginInput loginInput) {
		// TODO Auto-generated method stub
		String givenPassword = loginInput.getPassword();
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		Query query = new Query();
		query.addCriteria(Criteria.where("userEmail").is(loginInput.getUserEmail()));
		List<User> users = mongoTemplate.find(query, User.class, "user");
		if(users.size()==0) {
			throw new UserActionException("user is not registered");
		}
		String storedPasswordHashed = users.get(0).getPassword();
		if(bCryptPasswordEncoder.matches(givenPassword, storedPasswordHashed)) {
			return "yes";
		}
		return "no";
	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		Query query = new Query();
		query.addCriteria(Criteria.where("userEmail").is(email));
		List<User> users = mongoTemplate.find(query, User.class, "user");
		if(users.size()==0) {
			return null;
		}
		return users.get(0);
	}

	@Override
	public User updateUserPassword(String email,String password) {
		// TODO Auto-generated method stub
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = bCryptPasswordEncoder.encode(password);
		Query query = new Query();
		query.addCriteria(Criteria.where("userEmail").is(email));
		User user  = mongoTemplate.findAndModify(query, Update.update("password", hashedPassword), User.class);
		return user;
	}

}
