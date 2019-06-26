package com.tinyauction.service;

import java.util.Collection;
import java.util.List;

import com.tinyauction.dto.SubCategoryDto;
import com.tinyauction.entity.Category;
import com.tinyauction.entity.Currency;

public interface PropertiesService {
	
	public Collection<Currency> getAllCurrencies();
	
	public List<SubCategoryDto> getMainCategories();
	
	public String[] getDurations();
	
	public Collection<Category> getOtherCategories();

	public List<SubCategoryDto> getSearchCategories();
	
	public List<SubCategoryDto> getSubCategories();
	
	public int getDefaultPageSize();
}
