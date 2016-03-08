package com.crossover.trial.properties;

import java.io.InputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface PropertyFileHandler {
				
	Logger logger = LoggerFactory.getLogger(PropertyFileHandler.class);
	
	public Map<Object, Object> parse(InputStream stream);
	
	default void handlePropertyFile(String propUri, Map<String, String>  props, Map<String, ResourceTypeHandler> supportedResourceTypes) {
		boolean success = false;
		for (String resourceType : supportedResourceTypes.keySet()) {
			if(propUri.startsWith(resourceType)){
				supportedResourceTypes.get(resourceType).handleResourceType(propUri, props, this);
			}
		}
		if(!success){
			
			logger.error(propUri + " can not be loaded. Unsupported resouce type");
		}
	}
}
