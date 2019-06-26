package com.tinyauction.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tinyauction.entity.Role;

@Repository
public class RoleDaoImpl implements RoleDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public Role findByName(String roleName) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Role> theQuery = currentSession.createQuery("from Role where name=:roleName", Role.class);
		theQuery.setParameter("roleName", roleName);
		
		Role role = null;
		
		try {
			role = theQuery.getSingleResult();
		} catch (Exception e) {
			role = null;
		}
		
		return role;
		
	}

}
