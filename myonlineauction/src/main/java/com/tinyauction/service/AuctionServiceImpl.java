package com.tinyauction.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.tinyauction.constants.BidStatus;
import com.tinyauction.dao.BidDao;
import com.tinyauction.dao.CategoryDao;
import com.tinyauction.dao.CurrencyDao;
import com.tinyauction.dao.ImageDao;
import com.tinyauction.dao.ListingDao;
import com.tinyauction.dao.StatusDao;
import com.tinyauction.dao.UserDao;
import com.tinyauction.dto.AccountRow;
import com.tinyauction.dto.BidDto;
import com.tinyauction.dto.ListingDto;
import com.tinyauction.dto.ListingsRequest;
import com.tinyauction.entity.Bid;
import com.tinyauction.entity.Image;
import com.tinyauction.entity.Listing;
import com.tinyauction.entity.Status;
import com.tinyauction.filestorage.AwsS3CloudAccess;
import com.tinyauction.model.ListingModel;

@Service
public class AuctionServiceImpl implements AuctionService {

	private final static Logger LOGGER = Logger.getLogger(AuctionServiceImpl.class.getName());

	private final static int MILLIS_IN_DAY = 24 * 60 * 60 * 1000;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ImageDao imageDao;

	@Autowired
	private CurrencyDao currencyDao;

	@Autowired
	private StatusDao statusDao;

	@Autowired
	private ListingDao auctionDao;

	@Autowired
	private BidDao bidDao;

	@Autowired
	private AwsS3CloudAccess awsS3CloudAccess;
	

	@Value("${images_folder}")
	private String imagesDirectoryName;

	@Value("${last_chance}")
	private long lastChance;

	@Value("${bidPersentage}")
	private float bidPersentage;

	@Value("${pageSize}")
	private int pageSize;
	
	@Value("${auction_duration}")
	private String[] durations;

	@Override
	@Transactional
	public Long save(ListingDto auctionDto) {

		LOGGER.info(String.format("AuctionServiceImpl: save(%s)", auctionDto.toString()));

		long auctionStartTime = System.currentTimeMillis();
		long auctionEndTime = auctionStartTime + convertDaysToMillis(auctionDto.getDuration());
		Image image = new Image();
		image.setPath("");
		String originalFileName = auctionDto.getFile().getOriginalFilename();

		Listing auction = new Listing();
		auction.setName(auctionDto.getListingName());
		auction.setNumberOfBids(0);
		auction.setStartDate(new Timestamp(auctionStartTime));
		auction.setEndDate(new Timestamp(auctionEndTime));
		auction.setStartingPrice(Float.parseFloat(auctionDto.getStartPrice()));
		auction.setSeller(userDao.findByUserName(auctionDto.getSellerName()));
		auction.setCurrency(currencyDao.findCurrencyByName(auctionDto.getCurrency()));
		auction.setStatus(statusDao.getActiveStatus());
		auction.setCategory(categoryDao.findByName(auctionDto.getCategoryName()));
		auction.setDescription(auctionDto.getDescription());

		imageDao.saveImage(image);
		if (!auctionDto.getFile().isEmpty()) {
			String imagePath = imagesDirectoryName + "/" + auctionDto.getSellerName() + "/" + image.getId() + "."
					+ FilenameUtils.getExtension(originalFileName);

			image.setPath(imagePath);
			

			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(auctionDto.getFile().getContentType());
			metadata.setContentLength(auctionDto.getFile().getSize());
			metadata.setHeader("filename", auctionDto.getFile().getOriginalFilename());

			try (InputStream input = auctionDto.getFile().getInputStream()) {
				awsS3CloudAccess.uploadFile(imagePath, input, metadata);
			} catch (IOException e) {

				e.printStackTrace();
			}
		} else {
			image.setPath(awsS3CloudAccess.getNoImageAvailableKey());
		}

//		imageDao.saveImage(image);
		auction.setImage(image);
		auctionDao.saveListing(auction);
		LOGGER.info("New auction: " + auction);
		return auction.getId();
	}

	@Override
	@Transactional
	public Map<String, Object> findListingsByCategory(ListingsRequest listingsRequest) {

		LOGGER.info(String.format("AuctionServiceImpl: findListingsByCategory(%s)", listingsRequest.toString()));
		Status status = statusDao.getActiveStatus();
		int statusId = status.getId();
		List<Listing> listings;
		long listingsCount;
		int pageNumber = listingsRequest.getPageNumber();
		long currentTime;

		switch (listingsRequest.getCategoryName()) {
		case "New-Today":

			currentTime = System.currentTimeMillis();
			long startOfTheDay = (long) (Math.floor(((double) currentTime) / MILLIS_IN_DAY) * MILLIS_IN_DAY);
			listingsCount = auctionDao.countListingsNewToday(startOfTheDay, statusId);
			listings = auctionDao.findListingsCreatedAfter(categoryDao.getRoot().getName(), statusId, startOfTheDay,
					pageNumber, pageSize);
			LOGGER.info("startOfTheDay = " + startOfTheDay + " currentTime = " + currentTime);

			break;

		case "Ending-Today":

			currentTime = System.currentTimeMillis();
			long endOfTheDay = (long) (Math.ceil(((double) currentTime) / MILLIS_IN_DAY) * MILLIS_IN_DAY);
			listingsCount = auctionDao.countListingsEndingToday(endOfTheDay, statusId);
			listings = auctionDao.findExpiringListingsBefore(categoryDao.getRoot().getName(), statusId, endOfTheDay,
					pageNumber, pageSize);

			LOGGER.info("endOfTheDay = " + endOfTheDay + " currentTime = " + currentTime);

			break;

		case "Hot-50":

			listings = auctionDao.findHotListings(statusId, 50);
			listingsCount = 50;
			pageSize = 50;
			pageNumber = 1;
			break;

		case "Last-Chance":

			currentTime = System.currentTimeMillis();
			long lastChanceTime = currentTime + lastChance;
			listingsCount = auctionDao.countListingsLastChance(lastChanceTime, statusId);
			listings = auctionDao.findExpiringListingsBefore(categoryDao.getRoot().getName(), statusId, lastChanceTime,
					pageNumber, pageSize);

			LOGGER.info("lastChanceTime = " + lastChanceTime + " currentTime = " + currentTime);

			break;

		default:

			listingsCount = auctionDao.countListingsByParentCategory(listingsRequest.getCategoryName(), statusId);
			listings = auctionDao.findListingsByParentCategory(listingsRequest.getCategoryName(), statusId, pageNumber,
					pageSize);
		}

		LOGGER.info(String.format("AuctionServiceImpl: findListingsByCategory(){listings.size = %d;listingsCount=%d}",
				listings.size(), listingsCount));
		return buildListingsModel(listings, pageNumber, listingsCount, listingsRequest.getCategoryName());
	}

	@Override
	@Transactional
	public BidStatus placeBid(BidDto bidDto) {

		LOGGER.info("AuctionServiceImpl.placeBid (bidDto=" + bidDto + ")");

		Listing auction = auctionDao.findListing(bidDto.getAuctionId());

		if (auction == null)
			return BidStatus.LISTING_NOT_FOUND;

		if (auction.getSeller().getUserName().equals(bidDto.getBuyerUsername()))
			return BidStatus.SELF_BIDDING;

		float minBidPrice;

		if (auction.getWinningBid() == null) {
			minBidPrice = auction.getStartingPrice();
		} else {
			minBidPrice = Math.round(auction.getWinningBid().getAmount() * (1 + bidPersentage) * 100.0) / 100.0f;
		}

		if (minBidPrice > Float.parseFloat(bidDto.getBid()))
			return BidStatus.BID_IS_LESS_THEN_MIN;

		Bid bid = new Bid();
		bid.setBidder(userDao.findByUserName(bidDto.getBuyerUsername()));
		bid.setListing(auction);
		bid.setDate(new Timestamp(System.currentTimeMillis()));
		bid.setAmount(Float.parseFloat(bidDto.getBid()));

		bidDao.saveBid(bid);

		if (!auctionDao.updateWinningBid(bid))
			return BidStatus.BID_IS_LESS_THEN_MIN;

		
		return BidStatus.SUCCESS;
	}

	@Override
	@Transactional
	public ListingModel findListing(long auctionId) {

		Listing listing = auctionDao.findListing(auctionId);
		return fromAuctionToListingModel(listing, true);
	}

	@Override
	public Map<String, Object> searchListings(ListingsRequest listingsRequest) {
		int statusId = statusDao.getActiveStatus().getId();
		long listingsCount = auctionDao.countListingsByNameAndByCategory(listingsRequest.getCategoryName(),
				listingsRequest.getListingName(), statusId);
		List<Listing> listings = auctionDao.findListingsByNameAndByCategory(listingsRequest.getListingName(), statusId,
				listingsRequest.getCategoryName(), listingsRequest.getPageNumber(), pageSize);
		return buildListingsModel(listings, listingsRequest.getPageNumber(), listingsCount,
				listingsRequest.getCategoryName());
	}

	private long convertDaysToMillis(String duration) {
		int days = Integer.parseInt(duration.replace(" days", ""));
		return days * 24 * 60 * 60 * 1000;
	}

	private Map<String, Object> buildListingsModel(List<Listing> listings, int pageNumber, long listingsCount,
			String categoryName) {

		Map<String, Object> result = new HashMap<>();
		int numberOfPages = (int) Math.ceil(((float) listingsCount) / pageSize);

		int prevPage = -1, nextPage = -1;
		if (numberOfPages != 0) {

			if (pageNumber > 1)
				prevPage = pageNumber - 1;

			if (numberOfPages > pageNumber)
				nextPage = pageNumber + 1;

		}

		result.put("listings", listings.stream().map(listing -> fromAuctionToListingModel(listing, false))
				.collect(Collectors.toList()));
		result.put("currentPage", pageNumber);
		result.put("prevPage", prevPage);
		result.put("nextPage", nextPage);
		result.put("categoryName", categoryName);
		result.put("pages", pages(pageNumber, numberOfPages));

		return result;
	}

	private ListingModel fromAuctionToListingModel(Listing listing, boolean additioanlInfo) {
		ListingModel listingModel = new ListingModel();
		Bid currentBid = listing.getWinningBid();
		if (currentBid == null) {
			listingModel.setCurrentBid(listing.getStartingPrice() + " "+listing.getCurrency().getName());
		} else {
			listingModel.setCurrentBid(listing.getWinningBid().getAmount() + " "+listing.getCurrency().getName());
		}

		listingModel.setEndTime(listing.getEndDate().getTime());
		listingModel.setId(listing.getId());
		listingModel.setImagePath(awsS3CloudAccess.generatePresignedUrl(listing.getImage().getPath()));
//		listingModel.setImagePath("");
		listingModel.setName(listing.getName());
		listingModel.setNumberOfBids(listing.getNumberOfBids());
		if (additioanlInfo) {
			if (currentBid != null) {
				listingModel.setMinBid(
						Math.round(listing.getWinningBid().getAmount() * (1 + bidPersentage) * 100.0) / 100.0f
								+ listing.getCurrency().getName());
			} else {
				listingModel.setMinBid(listing.getStartingPrice() + listing.getCurrency().getName());
			}

			listingModel.setDescription(listing.getDescription());
		}

		return listingModel;
	}

	private int[] pages(int current, int numberOfPages) {

		int index = current - 5;
		int[] array = new int[numberOfPages];
		while (index < 1)
			index++;

		for (int i = 0; i < numberOfPages; i++, index++) {
			array[i] = index;
		}

		return array;
	}

	
	
	
	

	@Override
	@Transactional
	public void updateListingsStatus() {
		long currentTime = System.currentTimeMillis();
		auctionDao.updateListingsStatus(currentTime, statusDao.getActiveStatus().getId(), statusDao.getClosedStatus().getId());
		
	}

	@Override
	@Transactional
	public long nextTaskDate() {
		Date nextTaskDate = auctionDao.findClosestToEnd(statusDao.getActiveStatus().getId());
		if(nextTaskDate==null) {
			nextTaskDate = new Date(System.currentTimeMillis()+convertDaysToMillis(durations[0]));
			LOGGER.info("nextTaskDate");
		}
		return nextTaskDate.getTime();
	}

	@Override
	@Transactional
	public List<AccountRow> getCurrentBidsOfUser(String userName) {
		List<Listing> listings = auctionDao.findWinningListingsByUserAndStatus(userName, statusDao.getActiveStatus().getId());
		List<AccountRow> winning = listings.stream().map(listing->fromListingToAccountRow(listing, false)).collect(Collectors.toList());
		List<Listing> listings2 = auctionDao.findOutbidListingsByUserAndStatus(userName, statusDao.getActiveStatus().getId());
		List<AccountRow> outbid = listings2.stream().map(listing->fromListingToAccountRow(listing, true)).collect(Collectors.toList());
		winning.addAll(outbid);
		return winning;
	}

	@Override
	@Transactional
	public List<AccountRow> getOffersOfUser(String userName) {
		List<Listing> offers = auctionDao.findListingsOfUser(userName, statusDao.getActiveStatus().getId());
		List<AccountRow> rows = offers.stream().map(x->fromListingToAccountRow(x, false)).collect(Collectors.toList());
		return rows;
	}

	@Override
	@Transactional
	public List<AccountRow> getWonListings(String userName) {
		List<Listing> listings = auctionDao.findWinningListingsByUserAndStatus(userName, statusDao.getClosedStatus().getId());
		List<AccountRow> rows = listings.stream().map(listing->fromListingToAccountRow(listing, false)).collect(Collectors.toList());
		return rows;
	}

	
	private AccountRow fromListingToAccountRow(Listing listing, boolean outBid) {
		AccountRow accountRow = new AccountRow();
		accountRow.setListingId(listing.getId());
		accountRow.setDate(listing.getEndDate().toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		accountRow.setListingName(listing.getName());
		accountRow.setNumberOfBids(listing.getNumberOfBids()+" bids");
		accountRow.setOutbid(outBid);
		if(listing.getWinningBid()!=null)
			accountRow.setPrice(listing.getWinningBid().getAmount()+" "+listing.getCurrency().getName());
		else 
			accountRow.setPrice(listing.getStartingPrice()+" "+listing.getCurrency().getName());
		return accountRow;
	}
}


















