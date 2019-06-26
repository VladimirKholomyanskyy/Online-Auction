package com.tinyauction.service;

import org.springframework.context.event.ContextRefreshedEvent;


public interface UpdateService {
	public void onApplicationEvent(ContextRefreshedEvent event);
}
