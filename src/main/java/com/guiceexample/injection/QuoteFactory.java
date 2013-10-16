/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample.injection;

import java.util.Map;

import com.guiceexample.Quote;

public interface QuoteFactory
{
	Quote create(Map<String, String> fields);
}