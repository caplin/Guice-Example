/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample.modules;

import javax.inject.Inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.guiceexample.AuditLogger;
import com.guiceexample.QuoteService;
import com.guiceexample.YahooQuoteService;

public class OnlineModule extends AbstractModule
{
	@Override
	protected void configure()
	{
		
	}
	
	@Provides
	@Inject
	public QuoteService provideQuoteService(AuditLogger auditLogger)
	{
		return new YahooQuoteService(auditLogger);
	}
}