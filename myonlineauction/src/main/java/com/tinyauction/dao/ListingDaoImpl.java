package com.tinyauction.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.LockModeType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tinyauction.entity.Bid;
import com.tinyauction.entity.Listing;

@Repository
public class ListingDaoImpl implements ListingDao {
	
	private static final Logger LOGGER = Logger.getLogger(ListingDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void saveListing(Listing auction) {
		
		LOGGER.info(String.format("saveListing(%s)", auction.toString()));

		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(auction);
	}

	@Override
	public List<Listing> findListingsByParentCategory(String category,int statusId, int pageNumber, int auctionsPerPage) {
		
		LOGGER.info(String.format("findListingsByCategory(%s, %d, %d)", category, pageNumber, auctionsPerPage));
		
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Listing> query = currentSession.createQuery("from Listing where category.parent.name=:categoryName"
				+ " and status.id=:statusId", Listing.class);
		query.setParameter("categoryName", category);
		query.setParameter("statusId", statusId);
		query.setFirstResult((pageNumber-1)*auctionsPerPage);
		query.setMaxResults(auctionsPerPage);
		return query.getResultList();
	}

	@Override
	public List<Listing> findHotListings(int statusId, int amount) {
		
		LOGGER.info(String.format("findHotListings( %d)", amount));
		
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Listing> query = currentSession.createQuery("from Listing where status.id=:statusId order by numberOfBids DESC", Listing.class);
		query.setParameter("statusId", statusId);
		query.setFirstResult(0);
		query.setMaxResults(amount);
		return query.getResultList();
	}

	


	@Override
	public List<Listing> findExpiringListingsBefore(String categoryName, int statusId, long time, int pageNumber,
			int auctionsPerPage) {
		
		LOGGER.info(String.format("findExpiringListingsBefore(%s, %d, %d, %d)", categoryName, time, pageNumber, auctionsPerPage));
		
		
		Timestamp endTime = new Timestamp(time);
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Listing> query = currentSession.createQuery("from Listing where endDate<:endTime and status.id=:statusId",Listing.class);

		query.setParameter("endTime", endTime);
		query.setParameter("statusId", statusId);
		query.setFirstResult((pageNumber-1)*auctionsPerPage);
		query.setMaxResults(auctionsPerPage);
		return query.getResultList();
	}

	@Override
	public List<Listing> findListingsCreatedAfter(String categoryName, int statusId, long time, int pageNumber, int auctionsPerPage) {
		
		LOGGER.info(String.format("findListingsCreatedAfter(%s, %d, %d, %d)", categoryName, time, pageNumber, auctionsPerPage));
		
		Timestamp startTime = new Timestamp(time);
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Listing> query = currentSession.createQuery("from Listing where startDate>:startTime and status.id=:statusId",Listing.class);
		
		query.setParameter("startTime", startTime);
		query.setParameter("statusId", statusId);
		query.setFirstResult((pageNumber-1)*auctionsPerPage);
		query.setMaxResults(auctionsPerPage);
		return query.getResultList();
	}

	@Override
	public List<Listing> findListingsByNameAndByCategory(String name, int statusId, String category, int pageNumber, int auctionsPerPage) {
		
		LOGGER.info(String.format("findListingsByNameAndByCategory(%s, %s, %d, %d)", name, category, pageNumber, auctionsPerPage));
		
		
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Listing> query = currentSession.createQuery("from Listing where category.name=:categoryName "
				+ "and name=:auctionName and status.id=:statusId", Listing.class);
		query.setParameter("categoryName", category);
		query.setParameter("auctionName", name);
		query.setParameter("statusId", statusId);
		query.setFirstResult((pageNumber-1)*auctionsPerPage);
		query.setMaxResults(auctionsPerPage);
		return query.getResultList();
	}

	@Override
	public long countListingsByNameAndByCategory(String category, String auctionName, int statusId) {
		LOGGER.info(String.format("countListingsByNameAndByCategory(%s, %s)", category, auctionName));
		
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Long> query = currentSession.createQuery("select count(id) from Listing where category.name=:categoryName "
				+ "and name=:auctionName and status.id=:statusId", Long.class);
		query.setParameter("categoryName", category);
		query.setParameter("auctionName", auctionName);
		query.setParameter("statusId", statusId);
		return query.uniqueResult().intValue();
	}

	@Override
	public long countListingsByParentCategory(String category, int statusId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Long> query = currentSession.createQuery("select count(*) from Listing listing where "
				+ "listing.category.parent.name=:categoryName and status.id=:statusId", Long.class);
		query.setParameter("categoryName", category);
		query.setParameter("statusId", statusId);
		return query.uniqueResult().longValue();
		
//		Long size = (Long)currentSession.createQuery("select count (listing.id) from Listing listing").uniqueResult();
//		System.out.println("Size" + size);
//		return size;
	}

	@Override
	public long countListingsNewToday(long startOfTheDay, int statusId) {
		LOGGER.info(String.format("countListingsNewToday(%d)", startOfTheDay));

		Session currentSession = sessionFactory.getCurrentSession();
		Query<Long> query = currentSession.createQuery("select count(id) from Listing "
				+ "where startDate>:startTime and status.id=:statusId", Long.class);
		query.setParameter("startTime", new Timestamp(startOfTheDay));
		query.setParameter("statusId", statusId);
		return query.uniqueResult().intValue();
	}

	@Override
	public long countListingsEndingToday(long endOfTheDay, int statusId) {
		LOGGER.info(String.format("countListingsEndingToday(%d)", endOfTheDay));

		Session currentSession = sessionFactory.getCurrentSession();
		Query<Long> query = currentSession.createQuery("select count(id) from Listing "
				+ "where endDate<:endOfTheDay and status.id=:statusId", Long.class);
		query.setParameter("endOfTheDay", new Timestamp(endOfTheDay));
		query.setParameter("statusId", statusId);
		return query.uniqueResult().intValue();
	}

	@Override
	public long countListingsLastChance(long lastChanceTime, int statusId) {
		LOGGER.info(String.format("countListingsLastChance(%d)", lastChanceTime));
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Long> query = currentSession.createQuery("select count(id) from Listing "
				+ "where endDate<:lastChanceTime and status.id=:statusId", Long.class);
		query.setParameter("lastChanceTime", new Timestamp(lastChanceTime));
		query.setParameter("statusId", statusId);
		return query.uniqueResult().intValue();
	}

	@Override
	public Listing findListing(long listingId) {
		LOGGER.info(String.format("findListing(%d)", listingId));
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Listing> query = currentSession.createQuery("from Listing where id=:listingId",Listing.class);
		query.setParameter("listingId", listingId);
		
		return query.getSingleResult();
	}

	@Override
	public boolean updateWinningBid(Bid bid) {
		LOGGER.info(String.format("updateWinningBid(%s)", bid));
		
		Session currentSession = sessionFactory.getCurrentSession();
		Listing listingToUpdate = currentSession.find(Listing.class, bid.getListing().getId(), LockModeType.PESSIMISTIC_WRITE);
		
		if(listingToUpdate!=null) {
			if(listingToUpdate.getWinningBid()==null && listingToUpdate.getStartingPrice()<=bid.getAmount()) {
				listingToUpdate.setWinningBid(bid);
			}else if(listingToUpdate.getWinningBid()!=null && listingToUpdate.getWinningBid().getAmount()<bid.getAmount()) {
				listingToUpdate.setWinningBid(bid);
			}else {
				return false;
			}
			listingToUpdate.setNumberOfBids(listingToUpdate.getNumberOfBids().intValue()+1);
			currentSession.update(listingToUpdate);
			return true;
		}

		
		return false;
	}

	@Override
	public void updateListingsStatus(long currentTime, int currentStatus, int newStatus) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		@SuppressWarnings("rawtypes")
		Query query = currentSession.createQuery("update Listing set status.id=:newStatus "
				+ "where status.id=:currentStatus and endDate<=:currentTime");
		query.setParameter("newStatus", newStatus);
		query.setParameter("currentStatus", currentStatus);
		query.setParameter("currentTime", new Timestamp(currentTime));
		query.executeUpdate();
	}

	@Override
	public Date findClosestToEnd(int statusId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Date> query = currentSession.createQuery("select min(endDate) from Listing where status.id=:statusId",Date.class);
		query.setParameter("statusId", statusId);
		return query.getSingleResult();
	}

	@Override
	public List<Listing> findOutbidListingsByUserAndStatus(String userName, int status) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Listing> query = currentSession.createQuery("from Listing "
				+ "where status.id=:statusId and winningBid.bidder.userName!=:userName and id in (select listing.id from Bid where bidder.userName=:userName)",Listing.class);
		query.setParameter("statusId", status);
		query.setParameter("userName", userName);
		return query.getResultList();
	}

	@Override
	public List<Listing> findWinningListingsByUserAndStatus(String userName, int status) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Listing> query = currentSession.createQuery("from Listing "
				+ "where status.id=:statusId and winningBid.bidder.userName=:userName",Listing.class);
		query.setParameter("statusId", status);
		query.setParameter("userName",userName);
		return query.getResultList();
	}

	@Override
	public List<Listing> findListingsOfUser(String userName, int status) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Listing> query = currentSession.createQuery("from Listing "
				+ "where seller.userName=:userName and status.id=:statusId", Listing.class);
		query.setParameter("userName", userName);
		query.setParameter("statusId", status);
		return query.getResultList();
	}

	

}
