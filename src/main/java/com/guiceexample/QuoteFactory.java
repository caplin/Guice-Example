/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.guiceexample.util.AuditLogger;

@Singleton
public class QuoteFactory
{
	private final AuditLogger auditLogger;

	@Inject
	public QuoteFactory(AuditLogger auditLogger)
	{
		this.auditLogger = auditLogger;
	}
	
	public Quote create(Map<String, String> fields)
	{
		return new Quote(fields, auditLogger);
	}
}