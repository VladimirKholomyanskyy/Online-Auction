package com.tinyauction.dto;

public class AccountRow {
	private long listingId;
	private String listingName;
	private String price;
	private String numberOfBids;
	private String date;
	private boolean outbid;
	public long getListingId() {
		return listingId;
	}
	public void setListingId(long listingId) {
		this.listingId = listingId;
	}
	public String getListingName() {
		return listingName;
	}
	public void setListingName(String listingName) {
		this.listingName = listingName;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getNumberOfBids() {
		return numberOfBids;
	}
	public void setNumberOfBids(String numberOfBids) {
		this.numberOfBids = numberOfBids;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public boolean isOutbid() {
		return outbid;
	}
	public void setOutbid(boolean outbid) {
		this.outbid = outbid;
	}
	
	
}
