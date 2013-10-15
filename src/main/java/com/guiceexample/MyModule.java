/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample;

import java.util.concurrent.ScheduledExecutorService;

import javax.inject.Inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class MyModule extends AbstractModule
{
	@Override
	protected void configure()
	{
		bind(String.class).toInstance("EURUSD");
		
		//bind(ScheduledExecutorService.class).toInstance(Executors.newSingleThreadScheduledExecutor());
		bind(ScheduledExecutorService.class).toProvider(new ExecutorProvider());
	}
	
	@Provides
	@Inject
	public QuoteService provideQuoteService(AuditLogger auditLogger)
	{
		return new YahooQuoteService(auditLogger);
	}
}