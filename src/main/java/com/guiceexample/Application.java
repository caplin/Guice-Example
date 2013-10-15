/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Application implements FXQuoteListener
{
	private final String currencyPair;
	private final FXQuoteProvider quoteProvider;
	private final AuditLogger auditLogger;
	
	public static void main(String[] args)
	{
		String currencyPair = "EURUSD";
		ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
		AuditLogger auditLogger = new AuditLogger();
		
		YahooQuoteService quoteService = new YahooQuoteService(auditLogger);
		FXQuoteProvider quoteProvider = new FXQuoteProvider(quoteService, scheduledExecutor);
		Application application = new Application(currencyPair, quoteProvider, auditLogger);
		
		application.start();
	}
	
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