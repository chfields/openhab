package org.openhab.binding.itachflex.internal;

import java.util.HashMap;
import java.util.Map;

import org.openhab.core.binding.BindingConfig;

/**
 * This is a helper class holding binding specific configuration details
 * 
 * @author chfields
 * @since 1.8.0
 */
public class iTachFlexBindingConfig implements BindingConfig {

	private static final String TYPE_IR = "ir";
	private static final String TYPE_SERIAL = "serial";
	private String command;
	private String name;
	private String instance;
	private Map<String, String> commands = new HashMap<String, String>();
	private String type;
	private String port;
	

	public void setCommand(String string) {
		this.command = string;
	}
	// put member fields here which holds the parsed values

	public void setItemName(String name) {
		this.name = name;
	}
	

	public void setInstance(String string) {
		this.instance = string;
	}

	public String getInstance() {
		return this.instance;
	}

	public String getPath() {
		if (this.type.compareToIgnoreCase(TYPE_IR) == 0)
			return String.format("/irports/%s/sendir",this.port);
		else if (this.type.compareToIgnoreCase(TYPE_SERIAL) == 0)
			return String.format("/serialports/%s/sendserial",this.port);
		return null;
	}

	public String getCommand() {
		return this.command;
	}
	
	public String getItemName() {
		return this.name;
	}

	public void addActions(String action, String command) {
		commands.put(action, command);
	}

	public String getCommand(String name2) {
		return commands.get(name2);
	}

	public void setType(String string) {
		this.type = string;
	}

	public void setPort(String string) {
		this.port = string;
	}

	public String getType() {
		return type;
	}

	

}