package com.souschef.http.servlet.jaxrs;

import java.util.HashSet;
import java.util.Set;

public class JAXRApplication  extends javax.ws.rs.core.Application {
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(RecipeManagerService.class);
        //classes.add(JAXRApplicationContext.class);
        return classes;
    }
    
    
}
