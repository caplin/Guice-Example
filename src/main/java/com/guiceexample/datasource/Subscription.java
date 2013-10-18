/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample.datasource;

import java.util.Map.Entry;

import javax.inject.Inject;

import com.caplin.datasource.messaging.record.RecordType1Message;
import com.caplin.datasource.publisher.ActivePublisher;
import com.google.common.eventbus.Subscribe;
import com.google.inject.assistedinject.Assisted;
import com.guiceexample.Quote;
import com.guiceexample.util.AuditLogger;

public class Subscription
{
	private final String subject;
	private final String currencyPair;
	private final ActivePublisher publisher;
	private final AuditLogger auditLogger;
	
	private boolean hasSentInitialMessage = false;

	@Inject
	public Subscription(@Assisted String subject, @Assisted ActivePublisher publisher, AuditLogger auditLogger)
	{
		this.subject = subject;
		this.currencyPair = getCurrencyPairFrom(subject);
		this.publisher = publisher;
		this.auditLogger = auditLogger;
	}
	
	@Subscribe
	public void onQuote(Quote quote)
	{
		if(quote.getCurrencyPair().equals(this.currencyPair))
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
	
	private String getCurrencyPairFrom(String subject)
	{
		return subject.split("/")[2];
	}
}