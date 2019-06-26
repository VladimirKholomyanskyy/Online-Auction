package com.tinyauction.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tinyauction.dto.UserDto;
import com.tinyauction.entity.User;
import com.tinyauction.service.UserService;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	private UserService userService;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/showRegistrationForm")
	public String showRegisrationForm(Model model) {
//		List<SubCategoryDto> categories  = propertiesService.getSearchCategories();
		
//		model.addAttribute("categories", categories);
		model.addAttribute("userDto", new UserDto());
		
//		model.addAttribute("search", new ListingsRequest());

		
		return "registration";
	}

	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(@Valid @ModelAttribute("userDto") UserDto userDto,
			BindingResult theBindingResult, Model model) {

		String userName = userDto.getUserName();

		// form validation
		if (theBindingResult.hasErrors()) {

			return "registration";
		}

		// check the database if user already exists
		User existing = userService.findByUserName(userName);
		if (existing != null) {
			model.addAttribute("userDto", new UserDto());
			model.addAttribute("registrationError", "User name already exists.");

			return "registration";
		}
		// create user account
		userService.save(userDto);

		return "registration-confirmation";
	}

}
