package com.yeldan.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 * @author Umit Yeldan
 */
public class MyProperties {
	
	private Logger logger = LoggerFactory.getLogger(MyProperties.class);
	
	private Map<String, String>  props;

	public void loadProps(List<String> propUris) {
    	
    	
		 props = new HashMap<String, String>();
		
		//load properties from sources
		loadProperties(propUris);

    }

	    
    @SuppressWarnings("unchecked")
    private void loadProperties(List<String> propUris) {
    	for (String propUri : propUris) {
    		final Properties temp = new Properties();
    		if(propUri.endsWith(".properties") || propUri.endsWith(".json")){
				if(propUri.startsWith("file://")){
					if(propUri.endsWith(".properties")){
						try (final InputStream stream = new FileInputStream(propUri.replace("file://", ""))) {
						    temp.load(stream);
						}catch (IllegalArgumentException | IOException e) {
							//If an exception occur, the file will be skipped.
							//We just need to log the exception
							logger.error(propUri + " can not be loaded. ", e);
						}
						processProperties(temp);
					}else if(propUri.endsWith(".json")){
						JSONParser parser = new JSONParser();
						Object obj = null;
						
						try (final InputStream stream = new FileInputStream(propUri.replace("file://", ""))) {
							Reader reader = new InputStreamReader(stream);
							obj = parser.parse(reader);
							JSONObject jsonObject = (JSONObject) obj;
				            processProperties(jsonObject);
						}catch (IOException | ParseException e) {
							//If an exception occur, the file will be skipped.
							//We just need to log the exception
							logger.error(propUri + " can not be loaded. ", e);
						}

					}
					
				}else if(propUri.startsWith("http://")){
					URL url;
					if(propUri.endsWith(".properties")){
						try {
							url = new URL(propUri);
							try (final InputStream stream = url.openStream()) {
							    temp.load(stream);
							    
							}catch (IOException e) {
								//If an exception occur, the file will be skipped.
								//We just need to log the exception
								logger.error(propUri + " can not be loaded. ", e);
							}
							processProperties(temp);
						} catch (MalformedURLException e) {
							logger.error(propUri + " can not be loaded. ", e);
						}
						
					}else if(propUri.endsWith(".json")){
						try {
							url = new URL(propUri);
							JSONParser parser = new JSONParser();
							Object obj = null;
							try (final InputStream stream = url.openStream()) {
								Reader reader = new InputStreamReader(stream);
								obj = parser.parse(reader);
								JSONObject jsonObject = (JSONObject) obj;
					            processProperties(jsonObject);
							}catch (IOException | ParseException | IllegalArgumentException e) {
								//If an exception occur, the file will be skipped.
								//We just need to log the exception
								logger.error(propUri + " can not be loaded. ", e);
							}
							
						} catch (MalformedURLException e) {
							//If an exception occur, the file will be skipped.
							//We just need to log the exception
							logger.error(propUri + " can not be loaded. ", e);
						}					
			            
					}
					
				}else if(propUri.startsWith("classpath:resources")){
					if(propUri.endsWith(".properties")){
						try (final InputStream stream =
						           Main.class.getResourceAsStream(propUri.replace("classpath:resources", ""))) {
							if(stream == null){
								//the file will be skipped.
								//We just need to log the exception
								logger.error(propUri + " can not be loaded. ");
							}else{
								temp.load(stream);
							}
						    
						} catch (IOException e) {
							//If an exception occur, the file will be skipped.
							//We just need to log the exception
							logger.error(propUri + " can not be loaded. ", e);
						}
						processProperties(temp);
					}else if(propUri.endsWith(".json")){
						JSONParser parser = new JSONParser();
						Object obj = null;
						
						try (final InputStream stream =
						           Main.class.getResourceAsStream(propUri.replace("classpath:resources", ""))) {
							if(stream == null){
								//the file will be skipped.
								//We just need to log the exception
								logger.error(propUri + " can not be loaded. ");
							}else{
								Reader reader = new InputStreamReader(stream);
								obj = parser.parse(reader);
								JSONObject jsonObject = (JSONObject) obj;
					            processProperties(jsonObject);
							}
						} catch (IOException | ParseException | NullPointerException e) {
							//If an exception occur, the file will be skipped.
							//We just need to log the exception
							logger.error(propUri + " can not be loaded. ", e);
						}
						
			            
					}
				}else{
					//If the file type is not one of the expected types, the file will be skipped.
					//We just need to log 
					logger.error(propUri + " can not be loaded. Unsupported file type.");
				}
    		}else {
    			//If the file type is not one of the expected types, the file will be skipped.
				//We just need to log 
				logger.error(propUri + " can not be loaded. Unsupported file type.");
			}
    	}
	}


    
    //process given properties
    private void processProperties(Map<Object, Object>  temp) {
    	for (Object keyObject : temp.keySet()) {
			String key = (String)keyObject;
			//json mapper holds numeric values as number
			String val = null;
			try{
				val = (String) temp.get(key);
			}catch(ClassCastException ex){
				val = temp.get(key).toString();
			}
					
			key = key.replace(".", "_");
			
			props.put(key, val);

		}
    }
    
    public String get(String key){
    	return props.get(key);
    }


	public void print() {
		System.err.println(props);
	}
    
    
}
