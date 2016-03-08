package com.crossover.trial.properties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	private Map<String, ResourceTypeHandler> resourceTypes = new HashMap<>();
	private Map<String, PropertyFileHandler> supportedFileTypes = new HashMap<>();
	
	public void addResourceType(String resourceType, ResourceTypeHandler handler){
		resourceTypes.put(resourceType, handler);
	}

	public void addFileType(String resourceType, PropertyFileHandler handler){
		supportedFileTypes.put(resourceType, handler);
	}
	
	public void loadProps(List<String> propUris) {
    	
    	
		props = new HashMap<String, String>();
		
				
		//load properties from sources
		loadProperties(propUris, resourceTypes, supportedFileTypes);

    }

	   
    private void loadProperties(List<String> propUris, 
    		Map<String, ResourceTypeHandler> resourceTypes,
    		Map<String, PropertyFileHandler> supportedFileTypes) {
    	for (String propUri : propUris) {
    		
    		PropertyFileHandler handler = null;
    		try{		
    			handler = supportedFileTypes.get(propUri.substring(propUri.lastIndexOf(".")+1));
    		}catch(ClassCastException | NullPointerException e){
    			logger.error(propUri + " can not be loaded. ", e);
    		}
    		
    		if(handler != null){
    			handler.handlePropertyFile(propUri, props, resourceTypes);
    		}else{
    			//If an exception occur, the file will be skipped.
				//We just need to log the exception
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
