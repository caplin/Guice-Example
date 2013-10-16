/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class QuoteBuilder
{
	private final long baseQuoteID = System.currentTimeMillis();
	private final AtomicInteger quoteID = new AtomicInteger(1);
	private final QuoteFactory quoteFactory;
	
	@Inject
	public QuoteBuilder(QuoteFactory quoteFactory)
	{
		this.quoteFactory = quoteFactory;
	}
	
	public synchronized Quote createQuote(String currencyPair, double midPrice)
	{
		Map<String, String> fields = new HashMap<String, String>();
		double bid = midPrice - 0.0005d;
		double ask = midPrice + 0.0005d;
		
		// Custom fields
		fields.put("CurrencyPair", currencyPair);
		fields.put("L1_AllInBidRate", String.valueOf(bid));
		fields.put("SpotBidRate", String.valueOf(bid));
		fields.put("L1_AllInAskRate", String.valueOf(ask));
		fields.put("SpotAskRate", String.valueOf(ask));

		// Boring static fields
		fields.put("QuoteID", String.valueOf(baseQuoteID) + "_" + quoteID.getAndIncrement());
		fields.put("GFA", "1000000");
		fields.put("NumberOfFractionalPips", "1");
		fields.put("DigitsBeforePips", "2"); 
		fields.put("BidPoints", "0.00000"); 
		fields.put("AskIndicative", "false");
		fields.put("L1_Tenor", "SPOT"); 
		fields.put("AskPoints", "0.00000"); 
		fields.put("BaseCurrency", currencyPair.substring(0, 3));
		fields.put("TermCurrency", currencyPair.substring(3));
		fields.put("DealtCurrency", fields.get("BaseCurrency"));
		fields.put("BidIndicative", "false");
		fields.put("L1_FwdAskPoints", "0.00000"); 
		fields.put("DigitsBeforePoint", "2"); 
		fields.put("L1_FwdBidPoints", "0.00000");
		fields.put("NumberOfPips", "2");
		fields.put("TimePriceReceived", String.valueOf(System.currentTimeMillis()));
		
		return quoteFactory.create(fields);
	}
}