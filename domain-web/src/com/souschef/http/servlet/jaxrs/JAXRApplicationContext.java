package com.souschef.http.servlet.jaxrs;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.DeserializationConfig ;
import javax.ws.rs.core.MediaType;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JAXRApplicationContext  implements ContextResolver<ObjectMapper> {
	final ObjectMapper defaultObjectMapper;
	
	public JAXRApplicationContext() {
        defaultObjectMapper = createDefaultMapper();
	}


	private ObjectMapper createDefaultMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        
        mapper.getSerializationConfig().set(SerializationConfig.Feature.INDENT_OUTPUT, true);
        mapper.getSerializationConfig().set(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        mapper.getSerializationConfig().set(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.getSerializationConfig().set(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
        mapper.getSerializationConfig().set(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        mapper.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);       
        mapper.getDeserializationConfig().set(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        return mapper;
	}


    @Override
    public ObjectMapper getContext(Class<?> arg0) {
            return defaultObjectMapper;
    }
}
