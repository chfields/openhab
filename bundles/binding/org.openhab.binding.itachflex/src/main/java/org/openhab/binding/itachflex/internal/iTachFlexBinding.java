/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.itachflex.internal;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.openhab.binding.itachflex.iTachFlexBindingProvider;
import org.openhab.core.binding.AbstractActiveBinding;
import org.openhab.core.library.types.StringType;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;
import org.osgi.service.cm.ManagedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
	

/**
 * Implement this class if you are going create an actively polling service
 * like querying a Website/Device.
 * 
 * @author chfields
 * @since 1.8.0
 */
public class iTachFlexBinding extends AbstractActiveBinding<iTachFlexBindingProvider> implements ManagedService {

	private static final Logger logger = 
		LoggerFactory.getLogger(iTachFlexBinding.class);

	private static final Object CONFIG_SERVICE_PID = "service.pid";

	private static final Object CONFIG_HOST = "host";
	
	private Map<String, ITachFlexConnectionProperties> connections = new HashMap<String, ITachFlexConnectionProperties>();



	public iTachFlexBinding() {
	}
		
	
	
	/**
	 * Called by the SCR to deactivate the component when either the configuration is removed or
	 * mandatory references are no longer satisfied or the component has simply been stopped.
	 * @param reason Reason code for the deactivation:<br>
	 * <ul>
	 * <li> 0 – Unspecified
     * <li> 1 – The component was disabled
     * <li> 2 – A reference became unsatisfied
     * <li> 3 – A configuration was changed
     * <li> 4 – A configuration was deleted
     * <li> 5 – The component was disposed
     * <li> 6 – The bundle was stopped
     * </ul>
	 */
	public void deactivate(final int reason) {
	}

	


	/**
	 * @{inheritDoc}
	 */
	@Override
	protected String getName() {
		return "iTachFlex Refresh Service";
	}
	
	
	/**
	 * @{inheritDoc}
	 */
	@Override
	protected void execute() {
		// the frequently executed code (polling) goes here ...
		logger.debug("execute() method is called!");
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	protected void internalReceiveCommand(String itemName, Command command) {
		logger.debug("internalReceiveCommand({},{}) is called!", itemName, command);
		iTachFlexBindingConfig config = getConfig(itemName);
		getConnector(config).sendCommand(config, command);
		
		// Post update to clear state - therefore UI, etc. will release push button
		eventPublisher.postUpdate(itemName, new StringType(""));
	}
	
	private iTachFlexBindingConfig getConfig(String itemName) {
		for (iTachFlexBindingProvider provider : providers) {
			iTachFlexBindingConfig config = provider.getConfig(itemName);
			 if (config != null)
				 return config;
		}
			 
		return null;
	}



	private ITachFlexConnector getConnector(iTachFlexBindingConfig config) {
		
		return new ITachFlexConnector(connections.get(config.getInstance()));
	}



	/**
	 * @{inheritDoc}
	 */
	@Override
	protected void internalReceiveUpdate(String itemName, State newState) {
		// the code being executed when a state was sent on the openHAB
		// event bus goes here. This method is only called if one of the 
		// BindingProviders provide a binding for the given 'itemName'.
		logger.debug("internalReceiveUpdate({},{}) is called!", itemName, newState);
	}
	
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updated(Dictionary<String, ?> config) {
		if (config != null) {
			logger.debug("iTachFlex binding updated");
			
			Enumeration<String> keys = config.keys();
			
			while (keys.hasMoreElements()) {
				
				String key = keys.nextElement();
				if (CONFIG_SERVICE_PID.equals(key)) 
					continue;
				
				String[] parts = key.split("\\.");
				String value = ((String) config.get(key)).trim();
				
				
				String instance = parts[0];

				ITachFlexConnectionProperties connection = connections.get(instance);
				if (connection == null) {
					connection = new ITachFlexConnectionProperties();
					connection.setInstance(instance);
					connections.put(instance, connection);
				}

				String option = parts[1].trim();
				
				if (CONFIG_HOST.equals(option)) {
					connection.setHost(value);
				} 
				
			}
		}
	}


	@Override
	protected long getRefreshInterval() {
		return 60000;
	}
}
