/**
 * Copyright (c) 2010-2016, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.itachflex.internal;

/**
 * Connection properties for a iTachFlex device by hostname or IP
 * 
 * @author chris.fields
 *
 */
public class ITachFlexConnectionProperties {

	private String instance;
	private String host;

	public String getHost() {
		return this.host;
	}

	public String getInstance() {
		return this.instance;
	}

	/**
	 * Host name or IP of the iTachFlex instance
	 * @param value
	 */
	public void setHost(String value) {
		this.host = value;
	}

	/** 
	 * Name of the iTachflex host
	 * @param instance
	 */
	public void setInstance(String instance) {
		this.instance = instance;
	}

}
