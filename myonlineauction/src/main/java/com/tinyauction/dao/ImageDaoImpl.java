package com.tinyauction.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tinyauction.entity.Image;

@Repository
public class ImageDaoImpl implements ImageDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	

	@Override
	public void saveImage(Image image) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(image);

	}

}
