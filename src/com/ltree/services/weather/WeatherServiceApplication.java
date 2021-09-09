package com.ltree.services.weather;

import javax.ws.rs.core.Application;

//@javax.ws.rs.ApplicationPath("/rest") // URL pattern can be defined here or in web.xml
// Mapping in web.xml overrides path set by @ApplicationPath
public class WeatherServiceApplication extends Application {
    
    // JAX-RS will scan class files WEB-INF/classes and jars in WEB-INF/lib for classes
    // with @Path
    
    // To override default scanning, define getClasses() method and return a Set of classes
    // that JAX-RS should scan
    
}
