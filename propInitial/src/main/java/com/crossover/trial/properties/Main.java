package com.crossover.trial.properties;

import java.util.Arrays;
import java.util.List;

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
        myProperties.loadProps(propertySourceUris);
        myProperties.print();
    }
}
