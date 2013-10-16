/*
 * Copyright 1995-2013 Caplin Systems Ltd
 */
package com.guiceexample.datasource;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.caplin.datasource.ConnectionListener;
import com.caplin.datasource.PeerStatusEvent;
import com.guiceexample.util.AuditLogger;

@Singleton
public class LoggingConnectionListener implements ConnectionListener
{
	private final AuditLogger auditLogger;

	@Inject
	public LoggingConnectionListener(AuditLogger auditLogger)
	{
		this.auditLogger = auditLogger;
	}

	@Override
	public void onPeerStatus(PeerStatusEvent event)
	{
		auditLogger.log("Got a peer status event: " + event);
	}
}
