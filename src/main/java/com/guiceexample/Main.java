/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample;

public class Main implements FXQuoteListener
{
	private final FXQuoteProvider quoteProvider = new FXQuoteProvider();
	
	public static void main(String[] args)
	{
		new Main("EURUSD");
	}
	
	public Main(String currencyPair)
	{
		quoteProvider.subscribe(currencyPair, this);
	}

	@Override
	public void onQuote(String currencyPair, double rate)
	{
		System.out.println("Received a quote for " + currencyPair + ": " + rate);
	}
}