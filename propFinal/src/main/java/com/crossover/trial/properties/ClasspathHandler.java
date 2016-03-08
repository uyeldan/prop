package com.crossover.trial.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClasspathHandler extends AbstractResourceTypeHandler{
	
	private Logger logger = LoggerFactory.getLogger(ClasspathHandler.class);
	

	@Override
	public void handleResourceType(String propUri, Map<String, String> props,	PropertyFileHandler fileHandler) {
		try (final InputStream stream =
			           Main.class.getResourceAsStream(propUri.replace("classpath:resources", ""))) {
			if(stream == null){
				//the file will be skipped.
				//We just need to log the exception
				logger.error(propUri + " can not be loaded. ");
			}else{
				Map<Object, Object> temp = fileHandler.parse(stream);
				processProperties(temp, props);
			}
			    
		} catch (IOException e) {
			//If an exception occur, the file will be skipped.
			//We just need to log the exception
			logger.error(propUri + " can not be loaded. ", e);
		}
	}

}
