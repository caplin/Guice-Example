/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample;

import java.util.Map;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;
import com.guiceexample.util.AuditLogger;

public class Quote
{
	private final Map<String, String> fields;

	@Inject
	public Quote(@Assisted Map<String, String> fields, AuditLogger auditLogger)
	{
		this.fields = fields;
		
		auditLogger.log("New Quote created for: " + getCurrencyPair());
	}

	public String getCurrencyPair()
	{
		return fields.get("CurrencyPair");
	}
	
	public String getBid()
	{
		return fields.get("L1_AllInBidRate");
	}

	public String getAsk()
	{
		return fields.get("L1_AllInAskRate");
	}
}