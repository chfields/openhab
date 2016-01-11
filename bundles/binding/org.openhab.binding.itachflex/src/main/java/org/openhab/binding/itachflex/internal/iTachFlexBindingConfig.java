/**
 * Copyright (c) 2010-2016, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.itachflex.internal;

import java.util.HashMap;
import java.util.Map;

import org.openhab.core.binding.BindingConfig;

/**
 * This is a helper class holding binding specific configuration details
 * 
 * @author chfields
 * @since 1.9.0
 */
public class iTachFlexBindingConfig implements BindingConfig {

	private static final String TYPE_IR = "ir";
	private static final String TYPE_SERIAL = "serial";
	private String command; // Command string for IR or serial device
	private String name; // name of the item
	private String instance; // iTachFlex instance that the item command will be sent to
	// map of commands that can be defined by mappings in a sitemap config
	private Map<String, String> commands = new HashMap<String, String>();
	// type of iTachFlex device command IR or SERIAL
	private String type;
	// the specific IR port 1-3 or 1 for SERIAL
	private String port;

	/**
	 * Add a named command - e.g., UP, DOWN, POWER, etc.
	 * @param action Name of action passed by Switch in 
	 * @param command String holding IR or SERIAL command of iTachFlex device
	 */
	public void addActions(String action, String command) {
		commands.put(action, command);
	}

	// put member fields here which holds the parsed values

	public String getCommand() {
		return this.command;
	}

	/**
	 * Get command by named action
	 * @param action Name of action designated in item definition
	 * @return iTachFlex command to send
	 */
	public String getCommand(String action) {
		return commands.get(action);
	}

	public String getInstance() {
		return this.instance;
	}

	public String getItemName() {
		return this.name;
	}

	/**
	 * URL path to iTachFlex device based on type and port
	 * @return formatted url path
	 */
	public String getPath() {
		if (this.type.compareToIgnoreCase(TYPE_IR) == 0)
			return String.format("/irports/%s/sendir", this.port);
		else if (this.type.compareToIgnoreCase(TYPE_SERIAL) == 0)
			return String.format("/serialports/%s/sendserial", this.port);
		return null;
	}

	public String getType() {
		return type;
	}

	public void setCommand(String string) {
		this.command = string;
	}

	public void setInstance(String string) {
		this.instance = string;
	}

	public void setItemName(String name) {
		this.name = name;
	}

	public void setPort(String string) {
		this.port = string;
	}

	public void setType(String string) {
		this.type = string;
	}

}