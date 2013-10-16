/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample.datasource;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.caplin.datasource.DataSource;
import com.caplin.datasource.namespace.Namespace;
import com.caplin.datasource.publisher.ActivePublisher;
import com.caplin.datasource.publisher.DataProvider;
import com.caplin.datasource.publisher.DiscardEvent;
import com.caplin.datasource.publisher.RequestEvent;
import com.google.inject.name.Named;

@Singleton
public class RateProvider implements DataProvider
{
	private final ActivePublisher publisher;

	@Inject
	public RateProvider(DataSource dataSource, @Named("RatesNamespace") Namespace nameSpace)
	{
		this.publisher = dataSource.createActivePublisher(nameSpace, this);
	}
	
	@Override
	public void onDiscard(DiscardEvent discardEvent)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void onRequest(RequestEvent requestEvent)
	{
		// TODO Auto-generated method stub
	}
}