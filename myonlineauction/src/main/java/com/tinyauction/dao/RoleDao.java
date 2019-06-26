package com.tinyauction.dao;

import com.tinyauction.entity.Role;

public interface RoleDao {
	
	Role findByName(String roleName);
	
}
