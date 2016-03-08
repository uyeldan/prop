package com.crossover.trial.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileHandler extends AbstractResourceTypeHandler {
	
	private Logger logger = LoggerFactory.getLogger(FileHandler.class);
	

	@Override
	public void handleResourceType(String propUri, Map<String, String>  props, PropertyFileHandler fileHandler) {
		try (final InputStream stream = new FileInputStream(propUri.replace("file://", ""))) {
			Map<Object, Object> temp = fileHandler.parse(stream);
		    processProperties(temp, props);
		}catch (IllegalArgumentException | IOException e) {
			//If an exception occur, the file will be skipped.
			//We just need to log the exception
			logger.error(propUri + " can not be loaded. ", e);
		}

	}

}
