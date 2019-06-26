package com.tinyauction.dao;

import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tinyauction.entity.Currency;

@Repository
public class CurrencyDaoImpl implements CurrencyDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Collection<Currency> getAllCurrencies() {
		Session currentSession = sessionFactory.getCurrentSession();

		Query<Currency> query = currentSession.createQuery("from Currency", Currency.class);

		return query.getResultList();
	}

	@Override
	public Currency findCurrencyByName(String currencyName) {
		Session currentSession = sessionFactory.getCurrentSession();

		Query<Currency> query = currentSession.createQuery("from Currency where name=:currencyName", Currency.class);
		query.setParameter("currencyName", currencyName);
		Currency result;
		try {
			result = query.getSingleResult();
		}catch(Exception e) {
			result = null;
		}

		return result;
	}

}
