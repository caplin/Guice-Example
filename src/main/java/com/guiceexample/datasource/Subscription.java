/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample.datasource;

import java.util.Map.Entry;

import javax.inject.Inject;

import com.caplin.datasource.messaging.record.RecordType1Message;
import com.caplin.datasource.publisher.ActivePublisher;
import com.google.inject.assistedinject.Assisted;
import com.guiceexample.FXQuoteListener;
import com.guiceexample.Quote;
import com.guiceexample.util.AuditLogger;

public class Subscription implements FXQuoteListener
{
	private final String subject;
	private final ActivePublisher publisher;
	private final AuditLogger auditLogger;
	
	private boolean hasSentInitialMessage = false;

	@Inject
	public Subscription(@Assisted String subject, @Assisted ActivePublisher publisher, AuditLogger auditLogger)
	{
		this.subject = subject;
		this.publisher = publisher;
		this.auditLogger = auditLogger;
	}
	
	@Override
	public void onQuote(String currencyPair, Quote quote)
	{
		RecordType1Message message = publisher.getMessageFactory().createRecordType1Message(subject);
		
		for(Entry<String, String> field : quote.getFields().entrySet())
		{
			message.setField(field.getKey(), field.getValue());
		}
		
		if(!hasSentInitialMessage)
		{
			auditLogger.log("Sending initial quote for subject: " + subject);
			publisher.publishInitialMessage(message);
			hasSentInitialMessage = true;
		}
		else
		{
			auditLogger.log("Sending updated quote for subject: " + subject);
			publisher.publishToSubscribedPeers(message);
		}
	}
}