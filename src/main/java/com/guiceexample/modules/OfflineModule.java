/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample.modules;

import com.google.inject.AbstractModule;
import com.guiceexample.OfflineQuoteService;
import com.guiceexample.QuoteService;

public class OfflineModule extends AbstractModule
{
	@Override
	protected void configure()
	{
		bind(QuoteService.class).toInstance(new OfflineQuoteService());
	}
}