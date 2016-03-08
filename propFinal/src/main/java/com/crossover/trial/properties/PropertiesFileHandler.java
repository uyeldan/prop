package com.crossover.trial.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class PropertiesFileHandler implements PropertyFileHandler {

	@Override
	public Map<Object, Object> parse(InputStream stream) {
		Properties temp = new Properties();
		try {
			temp.load(stream);
		} catch (IOException e) {
			//the file will be skipped.
			//We just need to log the exception
			logger.error("properties file can not be loaded. ", e);
		}
		return temp;

	}

}
