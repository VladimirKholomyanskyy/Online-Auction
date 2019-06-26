package com.tinyauction.dao;

import com.tinyauction.entity.Status;

public interface StatusDao {
	
	
	
	Status getActiveStatus();
	
	Status getClosedStatus();
	
	Status getCanceledStatus();
}
