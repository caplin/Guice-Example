/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample;

import java.util.Map;

public interface QuoteFactory
{
	Quote create(Map<String, String> fields);
}