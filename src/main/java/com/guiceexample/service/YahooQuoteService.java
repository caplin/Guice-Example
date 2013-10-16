/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;

import com.guiceexample.util.AuditLogger;

public class YahooQuoteService implements QuoteService
{
	private final AuditLogger auditLogger;

	@Inject
	public YahooQuoteService(AuditLogger auditLogger)
	{
		this.auditLogger = auditLogger;
	}
	
	@Override
	public double getMidPrice(String currencyPair)
	{
		try
		{
			URL url = new URL("http://finance.yahoo.com/d/quotes.csv?s=" + currencyPair + "=X&f=sl1d1t1ba&e=.csv");
			
			auditLogger.log("Retrieving a quote from: " + url);
			
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