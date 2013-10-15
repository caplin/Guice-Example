///*
// * Copyright 1995-2013 Caplin Systems Ltd
// */
//package com.guiceexample;
//
//import static org.mockito.Mockito.verify;
//
//import java.util.concurrent.ScheduledExecutorService;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//@RunWith(MockitoJUnitRunner.class)
//public class FXQuoteProviderTest
//{
//	private FXQuoteProvider provider;
//	private @Mock FXQuoteListener listener;
//	private @Mock ScheduledExecutorService scheduledExecutor;
//	
//	@Before
//	public void setUp()
//	{
//		provider = new FXQuoteProvider(scheduledExecutor, null);
//	}
//	
//	@Test
//	public void itSchedulesAnUpdateTaskWhenISubscribe()
//	{
//		// When
//		provider.subscribe("GBPUSD", listener);
//		
//		// Then
//		// I want a task to be scheduled on the executor service
//	}
//	
//	@Test
//	public void itCallsBackOnMyListenerWhenTheQuoteIsReceived()
//	{
//		// Given
//		// my mock quote service will return a rate of 1.5 for GBPUSD
//		provider.subscribe("GBPUSD", listener);
//		
//		// When
//		// the scheduled task runs
//		
//		// Then
//		verify(listener).onQuote("GBPUSD", 1.5d);
//	}
//}