package com.yeldan.properties;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonFileHandler implements PropertyFileHandler{

	Logger logger = LoggerFactory.getLogger(JsonFileHandler.class);
	
		

	@SuppressWarnings("unchecked")
	@Override
	public Map<Object, Object>  parse(InputStream stream) {
		JSONParser parser = new JSONParser();
		Object obj = null;
		
		Reader reader = new InputStreamReader(stream);
		try {
			obj = parser.parse(reader);
		} catch (IOException | ParseException e) {
			//the file will be skipped.
			//We just need to log the exception
			logger.error("Json File can not be loaded. ", e);
		}
		JSONObject jsonObject = (JSONObject) obj;
		
		return jsonObject;
	}
	
}
