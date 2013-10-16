/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample.modules;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.google.inject.AbstractModule;

public class MyModule extends AbstractModule
{
	@Override
	protected void configure()
	{
		bind(String.class).toInstance("EURUSD");
		bind(ScheduledExecutorService.class).toInstance(Executors.newSingleThreadScheduledExecutor());
	}
}