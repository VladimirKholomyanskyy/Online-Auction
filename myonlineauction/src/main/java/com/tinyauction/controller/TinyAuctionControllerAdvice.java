package com.tinyauction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.tinyauction.dto.ListingsRequest;
import com.tinyauction.dto.SubCategoryDto;
import com.tinyauction.service.PropertiesService;

@ControllerAdvice
public class TinyAuctionControllerAdvice {
	
	@Autowired
	private PropertiesService propertiesService;
	
	@ModelAttribute
	public void addModelAttribute(Model model) {
		List<SubCategoryDto> searchCategories = propertiesService.getSearchCategories();
		model.addAttribute("search", new ListingsRequest());
		model.addAttribute("categories", searchCategories);
	}
}
