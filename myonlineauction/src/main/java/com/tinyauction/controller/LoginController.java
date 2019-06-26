package com.tinyauction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	
	@GetMapping("/showLoginPage")
	public String showLoginPage(Model model) {
		
//		List<SubCategoryDto> categories  = propertiesService.getSearchCategories();
//		
//		model.addAttribute("categories", categories);
//		model.addAttribute("search", new ListingsRequest());
		
		return "login";
	}
	

}
