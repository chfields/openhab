package org.openhab.binding.itachflex.internal;

public class ITachFlexConnectionProperties {

	private String instance;
	private String host;

	public void setInstance(String instance) {
		this.instance = instance;
	}
	
	public String getInstance() {
		return this.instance;
	}

	public void setHost(String value) {
		this.host = value;
	}

	public String getHost() {
		return this.host;
	}

}
