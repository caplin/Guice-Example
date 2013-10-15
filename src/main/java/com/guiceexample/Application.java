/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample;

public class Application implements FXQuoteListener
{
	private final String currencyPair;
	private final FXQuoteProvider quoteProvider;
	
	public static void main(String[] args)
	{
		Application application = new Application();
		application.start();
	}
	
	public Application()
	{
		this.currencyPair = "EURUSD";
		this.quoteProvider = new FXQuoteProvider();
	}
	
	public void start()
	{
		quoteProvider.subscribe(currencyPair, this);
	}

	@Override
	public void onQuote(String currencyPair, double rate)
	{
		String logMessage = "Received a quote for " + currencyPair + ": " + rate;
		System.out.println(logMessage);
		
		// I want to log this
	}
}