package com.tinyauction.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.tinyauction.dto.ListingDto;
import com.tinyauction.constants.BidStatus;
import com.tinyauction.dto.BidDto;
import com.tinyauction.dto.ListingsRequest;
import com.tinyauction.dto.SubCategoryDto;
import com.tinyauction.entity.Currency;
import com.tinyauction.model.ListingModel;
import com.tinyauction.service.AuctionService;
import com.tinyauction.service.PropertiesService;
import com.tinyauction.service.UserService;
import com.tinyauction.validator.ListingDtoValidator;

@Controller
@RequestMapping("/auction")
public class AuctionController {

	private final static Logger logger = Logger.getLogger(AuctionController.class.getName());

	@Autowired
	private AuctionService auctionService;

	@Autowired
	private PropertiesService propertiesService;
	
	@Autowired
	private ListingDtoValidator listingDtoValidotor;
	
	@InitBinder("newAuctionDto")
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
		dataBinder.setValidator(listingDtoValidotor);
	}	

	@GetMapping("/showNewAuctionForm")
	public String showNewAuctionForm(Model model) {

		logger.info("AuctionController: showNewAuctionForm()");

		List<SubCategoryDto> results = propertiesService.getSubCategories();

		logger.info("results=" + results);
		
		Collection<Currency> currencies = propertiesService.getAllCurrencies();
		String[] durations = propertiesService.getDurations();

		model.addAttribute("categoriesList", results);
		model.addAttribute("currencies", currencies);
		model.addAttribute("durations", durations);
		model.addAttribute("newAuctionDto", new ListingDto());

		return "new-auction";
	}

	@PostMapping("/processNewAuctionForm")
	public String processNewAuctionForm(@Valid @ModelAttribute("newAuctionDto") ListingDto auctionDto,
			BindingResult bindingResult,
			Model model,
			Principal principal) {

		logger.info("AuctionController: processNewAuctionForm()");

		if(bindingResult.hasErrors()) {
			model.addAttribute("categoriesList", propertiesService.getSubCategories());
			model.addAttribute("currencies", propertiesService.getAllCurrencies());
			model.addAttribute("durations", propertiesService.getDurations());
			return "new-auction";
		}
		auctionDto.setSellerName(principal.getName());
		Long id = auctionService.save(auctionDto);

		model.addAttribute("auctionDto", auctionDto);
		model.addAttribute("listing", auctionDto.getCategoryName() + "/" + id);
		return "auction-created-success";
	}

	@GetMapping("/{category}/{id}")
	public String showAuction(@PathVariable(name = "id") long auctionId, Model model) {

		logger.info("AuctionController: showAuction()");

		ListingModel listing = auctionService.findListing(auctionId);
//		List<SubCategoryDto> searchCategories = propertiesService.getSearchCategories();
		if (listing == null) {
			model.addAttribute("message", "Can't find the item you have been requested");
			return "exception";
		}

		BidDto bid = new BidDto();
		bid.setAuctionId(auctionId);
		model.addAttribute("listing", listing);
//		model.addAttribute("search", new ListingsRequest());
//		model.addAttribute("categories", searchCategories);
		model.addAttribute("bid", bid);
		logger.info("AuctionController: showAuction() bid=" + bid);
		return "auction";

	}

	@PostMapping("/placeBid")
	public String placeBid(@Valid @ModelAttribute("bid") BidDto bidDto,
			BindingResult bindingResult,
			Principal principal, Model model) {

		logger.info("AuctionController: placeBid()");
		ListingModel listing = auctionService.findListing(bidDto.getAuctionId());
		if(bindingResult.hasErrors()) {
			model.addAttribute("listing", listing);
			return "auction";
		}
		
		bidDto.setBuyerUsername(principal.getName());

		BidStatus result = auctionService.placeBid(bidDto);

		String message = "";
		switch (result) {
		case SUCCESS:
			message = "Your bid has been placed!";
			model.addAttribute("successMessage", "message");

			return "auction";
		case SELF_BIDDING:
			message = "You cannot bid on your own listings!";
			model.addAttribute("errorMessage", message);
			return "auction";
		case LISTING_NOT_FOUND:
			return "exception";

		case BID_IS_LESS_THEN_MIN:
			message = "Your bid is less than the minimum!";
			model.addAttribute("errorMessage", message);
			return "auction";
	
		}

		return String.format("redirect:Electronics/%s?message=%s", bidDto.getAuctionId(), message);

	}

	@GetMapping("/{categoryName}")
	public String getAuctions(ListingsRequest listingsRequest, Model model) {

		logger.info("AuctionController: getAuctions()");

		Map<String, Object> result = auctionService.findListingsByCategory(listingsRequest);

		if (result.isEmpty()) {
			model.addAttribute("message", "Can't find listings you have been requested");
			return "exception";
		}

		logger.info("AuctionController: getAuctions(){result = " + result + "}");

		model.addAllAttributes(result);

		return "list-of-auctions";

	}

	@GetMapping("/search")
	public String searchAuctions(Model model, ListingsRequest listingsRequest) {

		logger.info("AuctionController: searchAuctions()");

		Map<String, ?> result = auctionService.searchListings(listingsRequest);
//		model.addAttribute("search", new ListingsRequest());
		if (result.isEmpty()) {
			model.addAttribute("message", "Can't find listings you have been requested");
			return "exception";
		}

		model.addAllAttributes(result);

		return "list-of-auctions";

	}

}
