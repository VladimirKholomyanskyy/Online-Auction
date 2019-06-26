package com.tinyauction.dto;

public class ListingsRequest {
	
	private String listingName;
	private String categoryName;
	private int pageNumber = 1;

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

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	@Override
	public String toString() {
		return "ListingsRequest [listingName=" + listingName + ", categoryName=" + categoryName + ", pageNumber="
				+ pageNumber + "]";
	}

}
