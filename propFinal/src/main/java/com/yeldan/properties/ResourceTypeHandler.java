package com.yeldan.properties;

import java.util.Map;
import java.util.Properties;

public interface ResourceTypeHandler {

	void handleResourceType(String propUri, Map<String, String> props, PropertyFileHandler fileHandler);
}
