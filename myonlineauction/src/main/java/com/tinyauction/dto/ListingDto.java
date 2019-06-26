package com.tinyauction.dto;

import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

public class ListingDto {
	

	private String listingName;
	private String categoryName;
	private String startPrice;
	private Timestamp startDate;
	private String duration;
	private int numberOfBids;
	private String sellerName;
	private String currency;
	private String status;
	private MultipartFile file;
	private String description;
	
	
	public ListingDto() {
		System.out.println("In AuctionDto constructor");
	}


	

	public String getListingName() {
		return listingName;
	}




	public void setListingName(String listingName) {
		this.listingName = listingName;
	}




	public String getCategoryName() {
		return categoryName;
	}




	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}




	public void setDuration(String duration) {
		this.duration = duration;
	}




	public String getStartPrice() {
		return startPrice;
	}


	public void setStartPrice(String startPrice) {
		this.startPrice = startPrice;
	}


	public Timestamp getStartDate() {
		return startDate;
	}


	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}


	public String getDuration() {
		return duration;
	}


	public void setEndDate(String duration) {
		this.duration = duration;
	}


	public int getNumberOfBids() {
		return numberOfBids;
	}


	public void setNumberOfBids(int numberOfBids) {
		this.numberOfBids = numberOfBids;
	}


	public String getSellerName() {
		return sellerName;
	}


	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}


	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public MultipartFile getFile() {
		return file;
	}


	public void setFile(MultipartFile file) {
		this.file = file;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}




	@Override
	public String toString() {
		return "ListingDto [listingName=" + listingName + ", categoryName=" + categoryName + ", startPrice="
				+ startPrice + ", startDate=" + startDate + ", duration=" + duration + ", numberOfBids=" + numberOfBids
				+ ", sellerName=" + sellerName + ", currency=" + currency + ", status=" + status + ", file=" + file
				+ ", description=" + description + "]";
	}


	
	
	
	
	
}
