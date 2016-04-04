package com.yeldan.properties;

import java.util.Map;

public abstract class AbstractResourceTypeHandler implements ResourceTypeHandler{

	protected void processProperties(Map<Object, Object>  temp, Map<String, String>  props) {
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
}
