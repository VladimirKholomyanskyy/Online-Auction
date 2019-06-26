package com.tinyauction.dao;

import java.util.Date;
import java.util.List;

import com.tinyauction.entity.Bid;
import com.tinyauction.entity.Listing;

public interface ListingDao {
	
	void saveListing(Listing listing);
	
	public Listing findListing(long listingId);
	
	public List<Listing> findListingsByParentCategory(String categoryName, int statusId, int pageNumber, int listingsPerPage);
	
	public List<Listing> findHotListings(int statusId,int amount);
	
	public List<Listing> findListingsByNameAndByCategory(String name, int statusId, String categoryName, int pageNumber, int listingsPerPage);
	
	public List<Listing> findExpiringListingsBefore(String categoryName, int statusId, long time, int pageNumber, int listingsPerPage);
	
	public List<Listing> findListingsCreatedAfter(String categoryName, int statusId, long time, int pageNumber, int listingsPerPage);
	
	public long countListingsByNameAndByCategory(String categoryName, String listingName, int statusId );
	
	public long countListingsByParentCategory(String categoryName, int statusId);
	
	public long countListingsNewToday(long startOfTheDay, int statusId);
	
	public long countListingsEndingToday(long endOfTheDay, int statusId);
	
	public long countListingsLastChance(long lastChanceTime, int statusId);
	
	public boolean updateWinningBid(Bid bid);
	
	public void updateListingsStatus(long currentTime, int currentStatus, int newStatus);
	
	public Date findClosestToEnd(int statusId);
	
	public List<Listing> findOutbidListingsByUserAndStatus(String userName, int status);
	
	public List<Listing> findWinningListingsByUserAndStatus(String userName, int status);
	
	public List<Listing> findListingsOfUser(String userName, int status);
}








