/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample.datasource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.caplin.datasource.DataSource;
import com.caplin.datasource.namespace.Namespace;
import com.caplin.datasource.publisher.ActivePublisher;
import com.caplin.datasource.publisher.DataProvider;
import com.caplin.datasource.publisher.DiscardEvent;
import com.caplin.datasource.publisher.RequestEvent;
import com.google.inject.name.Named;
import com.guiceexample.FXQuoteProvider;
import com.guiceexample.injection.SubscriptionFactory;
import com.guiceexample.util.AuditLogger;

@Singleton
public class RateProvider implements DataProvider
{
	private final Map<String, Subscription> subjectToSubscriptionMap = new ConcurrentHashMap<>();
	
	private final ActivePublisher publisher;
	private final FXQuoteProvider fxQuoteProvider;
	private final SubscriptionFactory subscriptionFactory;
	private final AuditLogger auditLogger;

	@Inject
	public RateProvider(DataSource dataSource, 
			@Named("RatesNamespace") Namespace nameSpace, 
			FXQuoteProvider fxQuoteProvider, 
			SubscriptionFactory subscriptionFactory, 
			AuditLogger auditLogger)
	{
		this.fxQuoteProvider = fxQuoteProvider;
		this.publisher = dataSource.createActivePublisher(nameSpace, this);
		
		this.subscriptionFactory = subscriptionFactory;
		this.auditLogger = auditLogger;
	}
	
	@Override
	public void onRequest(RequestEvent requestEvent)
	{
		String subject = requestEvent.getSubject();
		auditLogger.log("Received a request for: " + subject);
		
		String currencyPair = subject.split("/")[2];
		Subscription subscription = subscriptionFactory.create(subject, publisher);
		subjectToSubscriptionMap.put(subject, subscription);
		
		fxQuoteProvider.subscribe(currencyPair, subscription);
	}
	
	@Override
	public void onDiscard(DiscardEvent discardEvent)
	{
		String subject = discardEvent.getSubject();
		auditLogger.log("Received a discard for: " + subject);
		
		Subscription subscription = subjectToSubscriptionMap.get(subject);
		fxQuoteProvider.unsubscribe(subscription.getCurrencyPair(), subscription);
	}
}