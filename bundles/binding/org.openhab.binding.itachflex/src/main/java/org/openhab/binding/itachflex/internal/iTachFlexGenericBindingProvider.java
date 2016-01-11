/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.itachflex.internal;

import org.openhab.binding.itachflex.iTachFlexBindingProvider;
import org.openhab.core.items.Item;
import org.openhab.core.library.items.StringItem;
import org.openhab.model.item.binding.AbstractGenericBindingProvider;
import org.openhab.model.item.binding.BindingConfigParseException;


/**
 * This class is responsible for parsing the binding configuration for iTachFlex items.
 * 
 * @author chfields
 * @since 1.9.0
 */
public class iTachFlexGenericBindingProvider extends AbstractGenericBindingProvider implements iTachFlexBindingProvider {


	private static final String COMMAND_PREFIX = "command=";

	/**
	 * {@inheritDoc}
	 */
	public String getBindingType() {
		return "itachflex";
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public void validateItemType(Item item, String bindingConfig) throws BindingConfigParseException {
		if (!(item instanceof StringItem)) {
			throw new BindingConfigParseException("item '" + item.getName()
					+ "' is of type '" + item.getClass().getSimpleName()
					+ "', only StringItems are allowed - please check your *.items configuration");
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processBindingConfiguration(String context, Item item, String bindingConfig) throws BindingConfigParseException {
		super.processBindingConfiguration(context, item, bindingConfig);
		iTachFlexBindingConfig config = new iTachFlexBindingConfig();
		
		
		bindingConfig =	bindingConfig.trim();
		
		if (bindingConfig.startsWith(COMMAND_PREFIX)) {
			config.setItemName(item.getName());
			config.setCommand(bindingConfig.replace(COMMAND_PREFIX, ""));
		} else {
			String[] parts = bindingConfig.split(",");
			config.setItemName(item.getName());
			config.setInstance(parts[0]);
			config.setType(parts[1]);
			config.setPort(parts[2]);
			for(int i = 3;i < parts.length; i++) {
				String[] actionParts = parts[i].split(":");
				iTachFlexBindingConfig foundCommandConfig = getConfig(actionParts[1]);
				String command = foundCommandConfig.getCommand();
				config.addActions(actionParts[0], command);
			}
		}
		
		addBindingConfig(item, config);		
	}
	

	@Override
	public iTachFlexBindingConfig getConfig(String itemName) {
		return (iTachFlexBindingConfig) bindingConfigs.get(itemName);
	}

	@Override
	public Boolean autoUpdate(String itemName) {
		// no autoupdate -- all items will always be off since the iTachFlex does not have a feedback loop
		return false;
	}
}
