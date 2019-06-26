package com.tinyauction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TinyAuctionController {
	
	@GetMapping("/")
	public String showHome(Model model) {
//		List<SubCategoryDto> categories  = propertiesService.getSearchCategories();
		
//		FilterProperties filterProperties = new FilterProperties();
//		filterProperties.setItemsPerPage(propertiesService.getDefaultPageSize());
//		filterProperties.setPageNumber(propertiesService.getDefaultPageNumber());
//		
//		model.addAttribute("categories", categories);
//		model.addAttribute("search", new ListingsRequest());
//		model.addAttribute("filterProperties", filterProperties);
		
		return "home";
	}
	
	@GetMapping("/help")
	public String showHelp() {
		return "help";
	}
		
	
}
