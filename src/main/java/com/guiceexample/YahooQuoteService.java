/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class YahooQuoteService implements QuoteService
{
	public YahooQuoteService()
	{
		
	}
	
	@Override
	public double getQuote(String currencyPair)
	{
		try
		{
			URL url = new URL("http://finance.yahoo.com/d/quotes.csv?s=" + currencyPair + "=X&f=sl1d1t1ba&e=.csv");
			
			// I want to log the URL I'm retrieving quotes from
			
			InputStream stream = url.openStream();
			List<String> lines = IOUtils.readLines(stream);
			String[] tokens = lines.get(0).split(",");
			
			return Double.parseDouble(tokens[1]);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}