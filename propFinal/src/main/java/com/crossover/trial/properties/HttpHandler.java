package com.crossover.trial.properties;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpHandler extends AbstractResourceTypeHandler {
	
	private Logger logger = LoggerFactory.getLogger(AbstractResourceTypeHandler.class);
	


	@Override
	public void handleResourceType(String propUri, Map<String, String> props, PropertyFileHandler fileHandler) {
		URL url;
		try {
			url = new URL(propUri);
			try (final InputStream stream = url.openStream()) {
				Map<Object, Object> temp = fileHandler.parse(stream);
			    processProperties(temp, props);
			}catch (IOException | IllegalArgumentException e ) {
				//If an exception occur, the file will be skipped.
				//We just need to log the exception
				logger.error(propUri + " can not be loaded. ", e);
			}
				
		} catch (MalformedURLException e) {
			logger.error(propUri + " can not be loaded. ", e);
		}
		
	}


}
