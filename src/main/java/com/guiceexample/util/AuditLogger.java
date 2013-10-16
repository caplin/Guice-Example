/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample.util;

import javax.inject.Singleton;

@Singleton
public class AuditLogger
{
	public void log(String message)
	{
		System.out.println(message);
	}
}