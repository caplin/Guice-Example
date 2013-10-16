/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample.injection;

import com.caplin.datasource.publisher.ActivePublisher;
import com.guiceexample.datasource.Subscription;

public interface SubscriptionFactory
{
	Subscription create(String subject, ActivePublisher publisher);
}