/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample;

import java.util.concurrent.Executors;

public class Main implements FXQuoteListener
{
	private final String currencyPair;
	private final FXQuoteProvider quoteProvider;
	private final AuditLogger auditLogger;
	
	public static void main(String[] args)
	{
		AuditLogger auditLogger = new AuditLogger();
		
		FXQuoteProvider quoteProvider = new FXQuoteProvider(Executors.newSingleThreadScheduledExecutor(), auditLogger);
		
		Main application = new Main("EURUSD", quoteProvider, auditLogger);
		
		application.start();
	}
	
	public Main(String currencyPair, FXQuoteProvider quoteProvider, AuditLogger auditLogger)
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