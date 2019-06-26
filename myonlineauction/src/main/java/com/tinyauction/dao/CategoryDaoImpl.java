package com.tinyauction.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tinyauction.entity.Category;

@Repository
public class CategoryDaoImpl implements CategoryDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public Category findByName(String categoryName) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Category> query = currentSession.createQuery("from Category where name=:categoryName", Category.class);
		query.setParameter("categoryName", categoryName);
		
		Category category = null;
		
		try {
			category = query.getSingleResult();
		}catch(Exception e) {
			category=null;
		}
		
		
		return category;
	}

	@Override
	public List<Category> findSubcategoriesOf(String parentCategoryName) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Category> query = currentSession.createQuery("from Category where parent.name=:parentCategoryName", Category.class);
		query.setParameter("parentCategoryName", parentCategoryName);
		
		return query.getResultList();
	}

	@Override
	public Category findById(int categoryId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Category> query = currentSession.createQuery("from Category where id=:categoryId", Category.class);
		query.setParameter("categoryId", categoryId);
		
		Category category = null;
		
		try {
			category = query.getSingleResult();
		}catch(Exception e) {
			category=null;
		}
		
		
		return category;
	}

	@Override
	public Category getRoot() {
		
		return findByName("All Categories");
	}

	@Override
	public List<Category> findCategoriesWithoutChildren() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Category> query = currentSession.createQuery("from Category where parent.name!=:rootCategory", Category.class);
		query.setParameter("rootCategory", "All Categories");
		List<Category> result = query.getResultList();
		//System.out.println("fffff"+result);
		return result;
	}

}
