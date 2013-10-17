/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.guiceexample.service.QuoteService;

@Singleton
public class FXQuoteProvider
{
	private final Multimap<String, FXQuoteListener> currencyPairToFXQuoteListenersMap = HashMultimap.create();
	private final Map<String, ScheduledFuture<?>> currencyPairToTaskHandleMap = new ConcurrentHashMap<>();
	
	private final QuoteService quoteService;
	private final ScheduledExecutorService scheduledExecutor;
	private final QuoteBuilder quoteBuilder;
	
	@Inject
	public FXQuoteProvider(QuoteService quoteService, ScheduledExecutorService scheduledExecutor, QuoteBuilder quoteBuilder)
	{
		this.quoteService = quoteService;
		this.scheduledExecutor = scheduledExecutor;
		this.quoteBuilder = quoteBuilder;
	}
	
	public void subscribe(final String currencyPair, FXQuoteListener listener)
	{
		currencyPairToFXQuoteListenersMap.put(currencyPair, listener);

		if(currencyPairToFXQuoteListenersMap.get(currencyPair).size() == 1)
		{
    		Runnable updateTask = new Runnable()
    		{
    			@Override
    			public void run()
    			{
    				double midPrice = quoteService.getMidPrice(currencyPair);
    				
    				Quote quote = quoteBuilder.createQuote(currencyPair, midPrice);
    				
    				for(FXQuoteListener listener : currencyPairToFXQuoteListenersMap.get(currencyPair))
    				{
    					listener.onQuote(currencyPair, quote);
    				}
    			}
    		};
    		
    		ScheduledFuture<?> taskHandle = scheduledExecutor.scheduleAtFixedRate(updateTask, 0, 2, TimeUnit.SECONDS);
    		currencyPairToTaskHandleMap.put(currencyPair, taskHandle);
		}
	}

	public void unsubscribe(String currencyPair, FXQuoteListener listener)
	{
		currencyPairToFXQuoteListenersMap.remove(currencyPair, listener);
		if(!currencyPairToFXQuoteListenersMap.containsKey(currencyPair))
		{
			ScheduledFuture<?> taskHandle = currencyPairToTaskHandleMap.get(currencyPair);
			taskHandle.cancel(false);
		}
	}
}