/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample.injection;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.inject.Inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.guiceexample.QuoteFactory;
import com.guiceexample.service.QuoteService;
import com.guiceexample.service.YahooQuoteService;
import com.guiceexample.util.AuditLogger;

public class MyModule extends AbstractModule
{
	@Override
	protected void configure()
	{
		bind(String.class).toInstance("EURUSD");
		bind(ScheduledExecutorService.class).toInstance(Executors.newSingleThreadScheduledExecutor());
		
		install(new FactoryModuleBuilder().build(QuoteFactory.class));
	}
	
	@Provides
	@Inject
	public QuoteService provideQuoteService(AuditLogger auditLogger)
	{
		return new YahooQuoteService(auditLogger);
	}
}