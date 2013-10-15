/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.google.inject.Provider;

public class ExecutorProvider implements Provider<ScheduledExecutorService>
{
	@Override
	public ScheduledExecutorService get()
	{
		return Executors.newSingleThreadScheduledExecutor();
	}
}
