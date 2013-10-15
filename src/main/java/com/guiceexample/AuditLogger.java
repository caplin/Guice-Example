/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.inject.name.Named;

@Singleton
public class AuditLogger
{
	@Inject
	public AuditLogger(@Named("LogFilePath") String logFilePath)
	{
		log("Log file path is: " + logFilePath);
	}
	
	public void log(String message)
	{
		System.out.println(message);
	}
}