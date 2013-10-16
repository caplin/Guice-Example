/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.caplin.datasource.ConnectionListener;
import com.caplin.datasource.DataSource;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.guiceexample.injection.MyModule;
import com.guiceexample.util.AuditLogger;

@Singleton
public class Application implements FXQuoteListener
{
	private final FXQuoteProvider quoteProvider;
	private final AuditLogger auditLogger;
	private final DataSource dataSource;
	
	public static void main(String[] args)
	{
		Injector injector = Guice.createInjector(new MyModule(args));
		Application application = injector.getInstance(Application.class);
		application.start();
	}
	
	@Inject
	public Application(DataSource dataSource, FXQuoteProvider quoteProvider, ConnectionListener connectionListener, AuditLogger auditLogger)
	{
		this.dataSource = dataSource;
		this.quoteProvider = quoteProvider;
		this.auditLogger = auditLogger;
		
		dataSource.addConnectionListener(connectionListener);
	}
	
	public void start()
	{
		dataSource.start();
	}

	@Override
	public void onQuote(String currencyPair, Quote quote)
	{
		String logMessage = "Received a quote for " + currencyPair + ": " + quote.getBid() + "/" + quote.getAsk();
		auditLogger.log(logMessage);
	}
}