package com.tinyauction.dao;

import java.util.List;

import com.tinyauction.entity.Category;

public interface CategoryDao {
	
	Category findByName(String categoryName);
	
	Category findById(int categoryId);
	
	Category getRoot();
	
	List<Category> findSubcategoriesOf(String parentCategory);
	
	List<Category> findCategoriesWithoutChildren();
}
