package com.tinyauction.dao;

import java.util.Collection;

import com.tinyauction.entity.Currency;

public interface CurrencyDao {
	
	Collection<Currency> getAllCurrencies();
	
	Currency findCurrencyByName(String currencyName);
}
