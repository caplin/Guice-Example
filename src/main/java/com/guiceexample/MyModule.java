/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.inject.Inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;

public class MyModule extends AbstractModule
{
	@Override
	protected void configure()
	{
		bind(String.class)
			.annotatedWith(Names.named("CurrencyPair"))
			.toInstance("EURUSD");
		
		bind(String.class)
			.annotatedWith(Names.named("LogFilePath"))
			.toInstance("/log.txt");
		
		bind(ScheduledExecutorService.class).toInstance(Executors.newSingleThreadScheduledExecutor());
	}
	
	@Provides
	@Inject
	public QuoteService provideQuoteService(AuditLogger auditLogger)
	{
		return new YahooQuoteService(auditLogger);
	}
}