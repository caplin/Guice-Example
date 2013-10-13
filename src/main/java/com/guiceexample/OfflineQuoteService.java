/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample;

import java.io.File;

public class OfflineQuoteService implements QuoteService
{
	public OfflineQuoteService()
	{
		File file = new File("location of file containing quotes");
		// read in the contents of the file, I/O stuff, store it in a map
	}
	
	@Override
	public double getQuote(String currencyPair)
	{
		return /* quote from the map */ 0;
	}
}