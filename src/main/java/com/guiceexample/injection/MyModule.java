/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample.injection;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.inject.Inject;

import com.caplin.datasource.ConnectionListener;
import com.caplin.datasource.DataSource;
import com.caplin.datasource.DataSourceFactory;
import com.caplin.datasource.namespace.Namespace;
import com.caplin.datasource.namespace.RegexNamespace;
import com.caplin.datasource.publisher.DataProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.name.Names;
import com.guiceexample.datasource.LoggingConnectionListener;
import com.guiceexample.datasource.RateProvider;
import com.guiceexample.service.QuoteService;
import com.guiceexample.service.YahooQuoteService;
import com.guiceexample.util.AuditLogger;

public class MyModule extends AbstractModule
{
	private final String[] args;

	public MyModule(String[] args)
	{
		this.args = args;
	}

	@Override
	protected void configure()
	{
		bind(ScheduledExecutorService.class).toInstance(Executors.newSingleThreadScheduledExecutor());
		
		install(new FactoryModuleBuilder().build(QuoteFactory.class));
		install(new FactoryModuleBuilder().build(SubscriptionFactory.class));
		
		bind(DataSource.class).toInstance(DataSourceFactory.createDataSource(args));
		
		bind(Namespace.class)
			.annotatedWith(Names.named("RatesNamespace"))
			.toInstance(new RegexNamespace("/FX/.+/.+/.+/.+/.+"));
		
		bind(ConnectionListener.class).to(LoggingConnectionListener.class);
		
		bind(DataProvider.class)
			.to(RateProvider.class)
			.asEagerSingleton();
	}
	
	@Provides
	@Inject
	public QuoteService provideQuoteService(AuditLogger auditLogger)
	{
		return new YahooQuoteService(auditLogger);
	}
}