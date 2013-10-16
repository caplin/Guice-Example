/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.guiceexample.modules.MyModule;
import com.guiceexample.modules.OfflineModule;
import com.guiceexample.modules.OnlineModule;

@Singleton
public class Application implements FXQuoteListener
{
	private final String currencyPair;
	private final FXQuoteProvider quoteProvider;
	private final AuditLogger auditLogger;
	
	public static void main(String[] args)
	{
		Injector injector;
		
		if(args.length > 0 && args[0].equals("--offline"))
		{
			injector = Guice.createInjector(new MyModule(), new OfflineModule());
		}
		else
		{
			injector = Guice.createInjector(new MyModule(), new OnlineModule());
		}
		
		Application application = injector.getInstance(Application.class);
		application.start();
	}
	
	@Inject
	public Application(String currencyPair, FXQuoteProvider quoteProvider, AuditLogger auditLogger)
	{
		this.currencyPair = currencyPair;
		this.quoteProvider = quoteProvider;
		this.auditLogger = auditLogger;
	}
	
	public void start()
	{
		quoteProvider.subscribe(currencyPair, this);
	}

	@Override
	public void onQuote(String currencyPair, double rate)
	{
		String logMessage = "Received a quote for " + currencyPair + ": " + rate;
		auditLogger.log(logMessage);
	}
}