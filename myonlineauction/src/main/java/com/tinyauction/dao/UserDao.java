package com.tinyauction.dao;

import com.tinyauction.entity.User;

public interface UserDao {
	
	User findByUserName(String userName);
	
	void save(User user);
}
