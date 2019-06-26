package com.tinyauction.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.tinyauction.dto.UserDto;
import com.tinyauction.entity.User;



public interface UserService extends UserDetailsService {
	
	User findByUserName(String userName);

    void save(UserDto userDto);
}
