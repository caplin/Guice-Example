/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample;

public interface FXQuoteListener
{
	void onQuote(String currencyPair, Quote quote);
}