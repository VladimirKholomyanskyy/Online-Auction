package com.tinyauction.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tinyauction.entity.Bid;

@Repository
public class BidDaoImpl implements BidDao {
	
	private static final Logger LOGGER = Logger.getLogger(BidDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void saveBid(Bid bid) {
		
		LOGGER.info(String.format("saveBid(%s)", bid.toString()));
		
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(bid);
	}

}
