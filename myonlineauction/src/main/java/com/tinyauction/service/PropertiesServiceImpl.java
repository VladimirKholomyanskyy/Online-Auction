package com.tinyauction.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tinyauction.dao.CategoryDao;
import com.tinyauction.dao.CurrencyDao;
import com.tinyauction.dto.SubCategoryDto;
import com.tinyauction.entity.Category;
import com.tinyauction.entity.Currency;

@Service
public class PropertiesServiceImpl implements PropertiesService {
	
	@Autowired
	private CurrencyDao currencyDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Value("${auction_duration}")
	private String[] durations;
	
	
	@Value("${pageSize}")
	private int pageSize;
	
	@Override
	@Transactional
	public Collection<Currency> getAllCurrencies() {
		
		return currencyDao.getAllCurrencies();
	}

	@Override
	@Transactional
	public List<SubCategoryDto> getMainCategories() {
		List<Category> categories = categoryDao.findSubcategoriesOf(categoryDao.getRoot().getName());
		
		return categories.stream().map(category->converToDto(category)).collect(Collectors.toList());
	}

	@Override
	public String[] getDurations() {
		 
		return durations;
	}

	@Override
	public Collection<Category> getOtherCategories() {
		//get Categories: New Today, Ending Today, Hot50, Last Chance
		return null;
	}

	@Override
	@Transactional
	public List<SubCategoryDto> getSearchCategories() {
		Category rootCategory = categoryDao.getRoot();
		List<Category> subCategories = categoryDao.findCategoriesWithoutChildren();
		subCategories.add(rootCategory);
		Collections.swap(subCategories, 0, subCategories.size()-1);
		subCategories.subList(1, subCategories.size()).sort(Comparator.comparing(Category::getName));

		return subCategories.stream().map(category->converToDto(category)).collect(Collectors.toList());
	}

	
	@Override
	@Transactional
	public List<SubCategoryDto> getSubCategories() {
		
		List<Category> subCategories = categoryDao.findCategoriesWithoutChildren();
		
		return subCategories.stream().map(category->converToDto(category)).collect(Collectors.toList());
	}
	
	
	public int getDefaultPageSize() {
		return pageSize;
	}
	
	private SubCategoryDto converToDto(Category category) {
		return modelMapper.map(category, SubCategoryDto.class);
	}
	
}
















