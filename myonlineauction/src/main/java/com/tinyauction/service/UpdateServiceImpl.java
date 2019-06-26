package com.tinyauction.service;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

@Service
public class UpdateServiceImpl implements UpdateService {

	private final static Logger LOGGER = Logger.getLogger(UpdateServiceImpl.class.getName());
	
	@Autowired
	private ThreadPoolTaskScheduler taskScheduler;
	
	@Autowired
	private AuctionService auctionService;
	
	
	@Override
	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		updateTask();
		
	}
	
	@Async
	private void updateTask() {
		LOGGER.info("updateTask");
		auctionService.updateListingsStatus();
		long nextTime = auctionService.nextTaskDate();
		long currentTime = System.currentTimeMillis();
		LOGGER.info("updateTask: nextTime = "+ new Date(nextTime));
		taskScheduler.getScheduledExecutor().schedule(()->updateTask(), nextTime - currentTime, TimeUnit.MILLISECONDS);
	}
	
}
