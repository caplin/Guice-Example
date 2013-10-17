/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.caplin.datasource.ConnectionListener;
import com.caplin.datasource.DataSource;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.guiceexample.injection.MyModule;

@Singleton
public class Application
{
	private final DataSource dataSource;
	private final ConnectionListener connectionListener;
	
	public static void main(String[] args)
	{
		Injector injector = Guice.createInjector(new MyModule(args));
		Application application = injector.getInstance(Application.class);
		application.start();
	}
	
	@Inject
	public Application(DataSource dataSource, ConnectionListener connectionListener)
	{
		this.dataSource = dataSource;
		this.connectionListener = connectionListener;
	}
	
	public void start()
	{
		dataSource.addConnectionListener(connectionListener);
		dataSource.start();
	}
}