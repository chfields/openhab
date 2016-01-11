package org.openhab.binding.itachflex.internal;

import org.apache.commons.io.IOUtils;
import org.openhab.core.library.types.StringType;
import org.openhab.core.types.Command;
import org.openhab.io.net.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ITachFlexConnector {
	
	private static final Logger logger = 
			LoggerFactory.getLogger(ITachFlexConnector.class);

	private static final String CONTEXT_TYPE_TEXT = "text-plain;char-set=utf-8";
	private static final String CONTENT_TYPE_JSON = "application/json";
	private ITachFlexConnectionProperties connectionProperties;
	static int timeout = 10000;

	public ITachFlexConnector(
			ITachFlexConnectionProperties iTachFlexConnectionProperties) {
		this.connectionProperties = iTachFlexConnectionProperties;
	}

	public void sendCommand(iTachFlexBindingConfig config, Command command) {
		
		String commandString = "";
		String commandName = ((StringType) command).toString();
		if (command instanceof StringType)
		{
			commandString = config.getCommand(commandName);
		} 
		logger.debug("iTachFlex Command={}",commandString);
		
		if (commandString==null)
		{
			logger.error("No command with the name={}",commandName);
			return;
		}
		
		String url = String.format("http://%s/api/v1%s", 
				connectionProperties.getHost(), config.getPath());
		
		logger.debug("iTachFlex HTTP call to URL= {}",url);
		
		String contentType = config.getType().compareToIgnoreCase("ir") == 0? CONTENT_TYPE_JSON : CONTEXT_TYPE_TEXT;
		HttpUtil.executeUrl("POST", url , 
				IOUtils.toInputStream(commandString), contentType, timeout);
		
		logger.debug("iTachFlex PostComplete!");
	}

}
