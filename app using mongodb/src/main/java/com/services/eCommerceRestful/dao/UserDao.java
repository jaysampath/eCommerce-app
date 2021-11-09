package com.services.eCommerceRestful.dao;

import com.services.eCommerceRestful.entity.User;
import com.services.eCommerceRestful.helpers.LoginInput;

public interface UserDao {

	public User newUserRegister(User user);

	public boolean checkUserExists(String userEmail);

	public String isUserAuthenticated(LoginInput loginInput);

	public User getUserByEmail(String email);
	
	public User updateUserPassword(String email,String password);

}
