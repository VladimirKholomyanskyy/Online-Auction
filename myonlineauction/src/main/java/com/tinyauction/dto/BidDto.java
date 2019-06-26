package com.tinyauction.dto;

import javax.validation.constraints.Pattern;

public class BidDto {
	
	private long auctionId;
	
	@Pattern(regexp = "^[0-9]+|([0-9]+\\.[0-9]{1,2})",message = "Invalid price!")
	private String bid;
	
	private String buyerUsername;
	
	
	public long getAuctionId() {
		return auctionId;
	}
	public void setAuctionId(long auctionId) {
		this.auctionId = auctionId;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getBuyerUsername() {
		return buyerUsername;
	}
	public void setBuyerUsername(String buyerUsername) {
		this.buyerUsername = buyerUsername;
	}
	@Override
	public String toString() {
		return "BidDto [auctionId=" + auctionId + ", bid=" + bid + ", buyerUsername=" + buyerUsername + "]";
	}
	
	
}
