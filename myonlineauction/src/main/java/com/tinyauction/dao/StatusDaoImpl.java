package com.tinyauction.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tinyauction.entity.Status;

@Repository
public class StatusDaoImpl implements StatusDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Status findStatusByName(String statusName) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Status> query = currentSession.createQuery("from Status where name=:statusName", Status.class);
		query.setParameter("statusName", statusName);
		Status result = null;
		try {
			result = query.getSingleResult();
		}catch(Exception e) {
			result = null;
		}
		return result;
	}

	@Override
	public Status getActiveStatus() {
	
		return findStatusByName("ACTIVE");
	}

	@Override
	public Status getClosedStatus() {
		
		return findStatusByName("CLOSED");
	}

	@Override
	public Status getCanceledStatus() {
		
		return findStatusByName("CANCELED");
	}

}
