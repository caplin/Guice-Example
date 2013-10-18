/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.common.eventbus.EventBus;
import com.guiceexample.service.QuoteService;

@Singleton
public class FXQuoteProvider
{
	private final Set<String> subscribedCurrencyPairs = new HashSet<String>();
	private final Map<String, ScheduledFuture<?>> currencyPairToTaskHandleMap = new ConcurrentHashMap<>();
	
	private final QuoteService quoteService;
	private final ScheduledExecutorService scheduledExecutor;
	private final QuoteBuilder quoteBuilder;
	private final EventBus eventBus;
	
	@Inject
	public FXQuoteProvider(QuoteService quoteService, 
			ScheduledExecutorService scheduledExecutor, 
			QuoteBuilder quoteBuilder,
			EventBus eventBus)
	{
		this.quoteService = quoteService;
		this.scheduledExecutor = scheduledExecutor;
		this.quoteBuilder = quoteBuilder;
		this.eventBus = eventBus;
	}
	
	public void subscribe(final String currencyPair)
	{
		if(!subscribedCurrencyPairs.contains(currencyPair))
		{
			subscribedCurrencyPairs.add(currencyPair);
			
    		Runnable updateTask = new Runnable()
    		{
    			@Override
    			public void run()
    			{
    				double midPrice = quoteService.getMidPrice(currencyPair);
    				
    				Quote quote = quoteBuilder.createQuote(currencyPair, midPrice);
    				eventBus.post(quote);
    			}
    		};
    		
    		ScheduledFuture<?> taskHandle = scheduledExecutor.scheduleAtFixedRate(updateTask, 0, 2, TimeUnit.SECONDS);
    		currencyPairToTaskHandleMap.put(currencyPair, taskHandle);
		}
	}

	public void unsubscribe(String currencyPair)
	{
		subscribedCurrencyPairs.remove(currencyPair);
		
		ScheduledFuture<?> taskHandle = currencyPairToTaskHandleMap.get(currencyPair);
		taskHandle.cancel(false);
	}
}