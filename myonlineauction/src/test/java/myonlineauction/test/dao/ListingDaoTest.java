package myonlineauction.test.dao;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.jboss.logging.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.tinyauction.config.TinyAuctionConfig;
import com.tinyauction.dao.CategoryDao;
import com.tinyauction.dao.CurrencyDao;
import com.tinyauction.dao.ImageDao;
import com.tinyauction.dao.ListingDao;
import com.tinyauction.dao.StatusDao;
import com.tinyauction.dao.UserDao;
import com.tinyauction.entity.Image;
import com.tinyauction.entity.Listing;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TinyAuctionConfig.class })
@WebAppConfiguration
public class ListingDaoTest {

	private static final Logger LOGGER = Logger.getLogger(ListingDaoTest.class);
	@Autowired
	private ListingDao listingDao;

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
	
	private long currentTime;
	
	
	
	
//	@Transactional
//	public void init() {
//		currentTime = System.currentTimeMillis();
//		populateDb(20, currentTime, 5, "phone");
//		populateDb(23, currentTime, 25, "mazda");
//		populateDb(25, currentTime, 15, "house");
//		populateDb(27, currentTime, 3, "food");
//	}

//	@Test
//	@Transactional
//	@Ignore
//	public void testSaveListing() {
//
//		
//
//		long size = listingDao.countListingsByParentCategory(categoryDao.findById(20).getName());
//
//		LOGGER.info("Size=" + size);
//
//		Assert.assertEquals(10, size);
//	}
//
//	@Ignore
//	@Test
//	@Transactional
//	public void testFindHotListingsByCategory() {
//		
//
//		List<Listing> listings = listingDao.findHotListings(4);
//		LOGGER.info("Listings size = " + listings.size());
//		for (Listing listing : listings) {
//			LOGGER.infof("*** %s", listing.toString());
//
//		}
//	}
//
//	@Ignore
//	@Test
//	@Transactional
//	public void findListingsByNameAndByCategory() {
//		
//
//		List<Listing> listings = listingDao.findListingsByNameAndByCategory("Listing",
//				categoryDao.findById(20).getName(), 1, 10);
//		List<Listing> listings2 = listingDao.findListingsByNameAndByCategory("Listing",
//				categoryDao.findById(20).getName(), 2, 10);
//		LOGGER.info("Listings size = " + listings.size());
//		for (Listing listing : listings) {
//			LOGGER.infof("*** %s", listing.toString());
//
//		}
//
//		LOGGER.info("Listings2 size = " + listings2.size());
//		for (Listing listing : listings2) {
//			LOGGER.infof("*** %s", listing.toString());
//
//		}
//	}
//
//	@Ignore
//	@Test
//	@Transactional
//	public void testFindExpiringListingsBefore() {
//		
//
//		List<Listing> listings = listingDao.findExpiringListingsBefore(categoryDao.findById(20).getName(),
//				System.currentTimeMillis() + 300000 * 7, 1, 10);
//		List<Listing> listings2 = listingDao.findExpiringListingsBefore(categoryDao.findById(21).getName(),
//				System.currentTimeMillis() + 300000 * 5, 1, 10);
//
//		LOGGER.info("testFindExpiringListingsBefore");
//
//		LOGGER.info("Listings size = " + listings.size());
//
//		for (Listing listing : listings) {
//			LOGGER.infof("*** %s", listing.toString());
//
//		}
//
//		LOGGER.info("Listings2 size = " + listings2.size());
//
//		for (Listing listing : listings2) {
//			LOGGER.infof("*** %s", listing.toString());
//
//		}
//	}
//
//	@Ignore
//	@Test
//	@Transactional
//	public void testFindListingsCreatedAfter() {
//		
//
//		List<Listing> listings = listingDao.findListingsCreatedAfter(categoryDao.findById(20).getName(),
//				System.currentTimeMillis() + 300000 * 7, 1, 10);
//		List<Listing> listings2 = listingDao.findListingsCreatedAfter(categoryDao.findById(21).getName(),
//				System.currentTimeMillis() + 300000 * 3, 1, 10);
//
//		LOGGER.info("testFindListingsCreatedAfter");
//
//		LOGGER.info("Listings size = " + listings.size());
////		for (Listing listing : listings) {
////			LOGGER.infof("*** %s", listing.toString());
////			
////		}
//
//		LOGGER.info("Listings2 size = " + listings2.size());
////		for (Listing listing : listings2) {
////			LOGGER.infof("*** %s", listing.toString());
////			
////		}
//	}
//
//	@Ignore
//	@Test
//	@Transactional
//	public void testCountListingsByNameAndByCategory() {
//		
//		
//		long size20 = listingDao.countListingsByNameAndByCategory(categoryDao.findById(20).getName(), "phone");
//		long size23 = listingDao.countListingsByNameAndByCategory(categoryDao.findById(23).getName(), "mazda");
//		long size25 = listingDao.countListingsByNameAndByCategory(categoryDao.findById(25).getName(), "house");
//		long size27 = listingDao.countListingsByNameAndByCategory(categoryDao.findById(27).getName(), "food");
//		long size0 = listingDao.countListingsByNameAndByCategory(categoryDao.findById(23).getName(), "food");
//		
//		
//		Assert.assertEquals(5, size20);
//		Assert.assertEquals(25, size23);
//		Assert.assertEquals(15, size25);
//		Assert.assertEquals(3, size27);
//		Assert.assertEquals(0, size0);
//		
//	}
//
//	@Ignore
//	@Test
//	@Transactional
//	public void testCountListingsByCategory() {
//		
//
//		long size20 = listingDao.countListingsByParentCategory(categoryDao.findById(20).getName());
//		long size23 = listingDao.countListingsByParentCategory(categoryDao.findById(23).getName());
//		long size25 = listingDao.countListingsByParentCategory(categoryDao.findById(25).getName());
//		long size27 = listingDao.countListingsByParentCategory(categoryDao.findById(27).getName());
//		long size0 = listingDao.countListingsByParentCategory(categoryDao.findById(29).getName());
//		
//		Assert.assertEquals(5, size20);
//		Assert.assertEquals(25, size23);
//		Assert.assertEquals(15, size25);
//		Assert.assertEquals(3, size27);
//		Assert.assertEquals(0, size0);
//	}
//
//	@Ignore
//	@Test
//	@Transactional
//	public void testCountListingsNewToday() {
//		
//		
//		long size1 = listingDao.countListingsNewToday(currentTime);
//		
//		long size2 = listingDao.countListingsNewToday(currentTime+ 300000);
//		
//		long size3 = listingDao.countListingsNewToday(currentTime+ 300000*25);
//		
//		Assert.assertEquals(48, size1);
//		Assert.assertEquals(44, size2);
//		Assert.assertEquals(0, size3);
//	}
//
//	@Ignore
//	@Test
//	@Transactional
//	public void testCountListingsEndingToday() {
//		
//		
//		
//		long size1 = listingDao.countListingsEndingToday(currentTime + 300000 * 2*26);
//		
//		long size2 = listingDao.countListingsEndingToday(currentTime+ 300000 * 2*24);
//		
//		long size3 = listingDao.countListingsEndingToday(currentTime);
//		
//		Assert.assertEquals(48, size1);
//		Assert.assertEquals(46, size2);
//		Assert.assertEquals(0, size3);
//
//	}
//	
//	@Ignore
//	@Test
//	@Transactional
//	public void testCountListingsLastChance() {
//		
//		
//		
//		long size1 = listingDao.countListingsLastChance(currentTime + 300000 * 2*26);
//		
//		long size2 = listingDao.countListingsLastChance(currentTime+ 300000 * 2*24);
//		
//		Assert.assertEquals(48, size1);
//		Assert.assertEquals(46, size2);
//
//	}
//	
//
//	private void populateDb(int categoryId, long currentTime, int amount, String listingName) {
//
//		for (int i = 1; i <= amount; i++) {
//			Listing listing = new Listing();
//			Image image = new Image();
//			image.setPath("/path/a" + i);
//			listing.setCategory(categoryDao.findById(categoryId));
//			listing.setCurrency(currencyDao.findCurrencyByName("USD"));
//			listing.setDescription("description" + i);
//			listing.setEndDate(new Timestamp(currentTime + 300000 * 2 * i));
//			listing.setImage(image);
//			listing.setName(listingName);
//			listing.setNumberOfBids(i);
//			listing.setSeller(userDao.findByUserName("batman"));
//			listing.setStartDate(new Timestamp(currentTime + 300000 * i));
//			listing.setStartingPrice(2.3f * (i + 1));
//			listing.setStatus(statusDao.getActiveStatus());
//
//			listingDao.saveListing(listing);
//		}
//	}
}
