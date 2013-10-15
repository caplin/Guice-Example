/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FXQuoteProvider
{
	private final QuoteService quoteService;
	private final ScheduledExecutorService scheduledExecutor;
	
	@Inject
	public FXQuoteProvider(QuoteService quoteService, ScheduledExecutorService scheduledExecutor)
	{
		this.quoteService = quoteService;
		this.scheduledExecutor = scheduledExecutor;
	}
	
	public void subscribe(final String currencyPair, final FXQuoteListener listener)
	{
		Runnable updateTask = new Runnable()
		{
			@Override
			public void run()
			{
				double quote = quoteService.getQuote(currencyPair);
				listener.onQuote(currencyPair, quote);
			}
		};
		
		scheduledExecutor.scheduleAtFixedRate(updateTask, 0, 1, TimeUnit.SECONDS);
	}
}