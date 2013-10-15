/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample;

import static org.mockito.Mockito.*;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FXQuoteProviderTest
{
	private static final String CURRENCY_PAIR = "GBPUSD";

	private FXQuoteProvider provider;
	
	private @Mock FXQuoteListener listener;
	private @Mock QuoteService quoteService;
	private @Mock ScheduledExecutorService scheduledExecutor;
	
	@Before
	public void setUp()
	{
		provider = new FXQuoteProvider(quoteService, scheduledExecutor);
	}
	
	@Test
	public void itSchedulesAnUpdateTaskWhenISubscribe()
	{
		// When
		provider.subscribe(CURRENCY_PAIR, listener);
		
		// Then
		verify(scheduledExecutor).scheduleAtFixedRate(any(Runnable.class), eq(0l), eq(1l), eq(TimeUnit.SECONDS));
	}
	
	@Test
	public void itCallsBackOnMyListenerWhenTheQuoteIsReceived()
	{
		// Given
		when(quoteService.getQuote(CURRENCY_PAIR)).thenReturn(1.5d);
		ArgumentCaptor<Runnable> argument = ArgumentCaptor.forClass(Runnable.class);
		provider.subscribe(CURRENCY_PAIR, listener);
		
		// When
		verify(scheduledExecutor).scheduleAtFixedRate(argument.capture(), eq(0l), eq(1l), eq(TimeUnit.SECONDS));
		argument.getValue().run();
		
		// Then
		verify(listener).onQuote(CURRENCY_PAIR, 1.5d);
	}
}