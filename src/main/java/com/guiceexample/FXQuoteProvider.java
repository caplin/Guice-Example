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

import com.guiceexample.service.QuoteService;

@Singleton
public class FXQuoteProvider
{
	private final Map<String, Set<FXQuoteListener>> currencyPairToFXQuoteListenersMap = new ConcurrentHashMap<>();
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
		Set<FXQuoteListener> listeners = currencyPairToFXQuoteListenersMap.get(currencyPair);
		
		if(listeners != null)
		{
			listeners.add(listener);
		}
		else
		{
			listeners = new HashSet<FXQuoteListener>();
			listeners.add(listener);
			currencyPairToFXQuoteListenersMap.put(currencyPair, listeners);
		
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
		Set<FXQuoteListener> subscriptions = currencyPairToFXQuoteListenersMap.get(currencyPair);
		if(subscriptions != null)
		{
			subscriptions.remove(listener);
			if(subscriptions.isEmpty())
			{
				currencyPairToFXQuoteListenersMap.remove(currencyPair);
				ScheduledFuture<?> taskHandle = currencyPairToTaskHandleMap.get(currencyPair);
				taskHandle.cancel(false);
			}
		}
	}
}