package com.tinyauction.service;

import java.util.List;
import java.util.Map;

import com.tinyauction.constants.BidStatus;
import com.tinyauction.dto.AccountRow;
import com.tinyauction.dto.BidDto;
import com.tinyauction.dto.ListingDto;
import com.tinyauction.dto.ListingsRequest;
import com.tinyauction.model.ListingModel;

public interface AuctionService {

	public Long save(ListingDto auctionDto);

	public ListingModel findListing(long auctionId);

	public Map<String, Object> findListingsByCategory(ListingsRequest listingsRequest);
	
	public Map<String,?> searchListings(ListingsRequest listingsRequest);

	public BidStatus placeBid(BidDto bidDto);
	
	public void updateListingsStatus();
	
	public long nextTaskDate();
	
	public List<AccountRow> getCurrentBidsOfUser(String userName);
	
	public List<AccountRow> getOffersOfUser(String usenName);
	
	public List<AccountRow> getWonListings(String userName);
}
