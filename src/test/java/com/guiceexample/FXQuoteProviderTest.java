/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FXQuoteProviderTest
{
	private FXQuoteProvider provider;
	private @Mock FXQuoteListener listener;
	
	@Before
	public void setUp()
	{
		provider = new FXQuoteProvider();
	}
	
	@Test
	public void it()
	{
		provider.subscribe("GBPUSD", listener);
	}
}