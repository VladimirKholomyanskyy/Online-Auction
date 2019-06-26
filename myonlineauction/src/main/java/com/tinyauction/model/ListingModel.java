package com.tinyauction.model;

public class ListingModel {

	private long id;
	private String name;
	private String currentBid;
	private int numberOfBids;
	private long endTime;
	private String imagePath;
	private String minBid;
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrentBid() {
		return currentBid;
	}

	public void setCurrentBid(String currentBid) {
		this.currentBid = currentBid;
	}

	public int getNumberOfBids() {
		return numberOfBids;
	}

	public void setNumberOfBids(int numberOfBids) {
		this.numberOfBids = numberOfBids;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getMinBid() {
		return minBid;
	}

	public void setMinBid(String minBid) {
		this.minBid = minBid;
	}

}
