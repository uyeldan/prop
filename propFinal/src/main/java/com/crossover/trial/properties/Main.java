package com.crossover.trial.properties;

import java.util.Arrays;
import java.util.List;

import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

/**
 * 
 * @author Umit Yeldan
 */
public class Main {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        List<String> propertySourceUris = Arrays.asList(args);
        
        
        MyProperties myProperties = new MyProperties();
        
        myProperties.addFileType(".json", new JsonFileHandler());

        myProperties.addResourceType("http", new HttpHandler());
        
        myProperties.loadProps(propertySourceUris);
        myProperties.print();
    }
}
