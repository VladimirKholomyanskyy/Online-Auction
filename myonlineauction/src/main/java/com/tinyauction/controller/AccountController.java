package com.tinyauction.controller;

import java.security.Principal;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tinyauction.dto.AccountRow;
import com.tinyauction.service.AuctionService;

@Controller
@RequestMapping("/account")
public class AccountController {

	private final static Logger LOGGER = Logger.getLogger(AccountController.class.getName());

	@Autowired
	private AuctionService auctionService;

	@GetMapping("/information")
	public String getAccountInformation(Model model, Principal principal) {

		LOGGER.info("getAccountInformation");
//		List<SubCategoryDto> searchCategories = propertiesService.getSearchCategories();
		List<AccountRow> bids = auctionService.getCurrentBidsOfUser(principal.getName());
		List<AccountRow> offers = auctionService.getOffersOfUser(principal.getName());
		List<AccountRow> listings = auctionService.getWonListings(principal.getName());
		
		LOGGER.info("bids size = " + bids.size());
		LOGGER.info("offers size = " + offers.size());
		LOGGER.info("listings size = " + listings.size());
		
//		model.addAttribute("search", new ListingsRequest());
//		model.addAttribute("categories", searchCategories);
		model.addAttribute("bids", bids);
		model.addAttribute("offers", offers);
		model.addAttribute("listings", listings);
		return "account";
	}
}














