package com.souschef.json.encoder;

import static com.souschef.json.JSONUtils.isIncluded;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.souschef.json.JSONUtils;
import com.souschef.json.parser.JSONParser;

/**
 *<p>This <code>com.castlebreck.json.encoder.JSONEncoder</code> descendant encodes whole beans hierarchies. The caller only needs to pass 
 *the hierarchy tree's root element to any of the public <code>encode</code> methods.</p>
 *<p>A bean hierarchy is a tree built up with beans that references  other beans through standard JavaBeans get/set property methods; be aware that stack overflow may occur if the hiearchy contains cycles.</p>
 *@author rsolano
 */
public class Bean2JSONEncoder extends JSONEncoder{
	/**
	 * <p>Singleton instance</p>
	 */
	public static Bean2JSONEncoder instance = new Bean2JSONEncoder();
	
	/**
	 *<p>This is the name of the extra element added to each object (javascript hash) to indicate the java class of the encoded object.</p>
	 */
	private String javaClassMetaData = "JAVA_CLASS_METADATA";
	
	/**
	 *<p>This is the name of the field that contains the full qualified class name of the encoded object.</p>
	 */
	private String javaClassName = "JAVA_CLASS_NAME";
	
	/**
	 *<p>This is the name of the array field that contains all generic types bound to the class of the encoded object (specified by <code>javaClassMetaData</code>).</p>
	 *<p>If the class class does not use generics, this field array is not included.</p>
	 */
	private String javaGenerics = "JAVA_CLASS_GENERICS";
	
	/**
	 * User defined encoders.
	 */
	private Map<String,Bean2JSONEncoderDelegate> delegates = new HashMap<String,Bean2JSONEncoderDelegate>();
	
	/**
	 *<p>Creates a JSON string containing <code>bean</code>'s properties. The next table describes the translation policy used by this method.</p>
	 * 
	 * <table>
	 * 		<tr>
	 * 			<td align="center" colspan="2" style="fcolor: green; font-weight:bold;">Java to JSON conversion table</b></td>
	 * 		</tr>
	 * 		<tr>
	 * 			<td align="left" style="border-bottom: 1px solid black; border-top: 1px solid black; color: green; font-weight:bold;">Java Data</td>
	 * 			<td align="left" style="border-bottom: 1px solid black; border-top: 1px solid black; color: green; font-weight:bold;">JSON Data</td>
	 * 		</tr>
	 * 		<tr>
	 * 			<td align="left" valign="top">java.lang.String</td>
	 * 			<td align="left" valign="top">A valid JavaScript string.</td>
	 * 		</tr>
	 * 		<tr>
	 * 			<td align="left" valign="top">boolean or Boolean</td>
	 * 			<td align="left" valign="top">Boolean literal (<code>true</code> or <code>false</code>).</td>
	 * 		</tr>
	 * 		<tr>
	 * 			<td align="left" valign="top">Integer (byte, int, short, long) types and their wrappers.</td>
	 * 			<td align="left" valign="top">JavaScript integer.</td>
	 * 		</tr>
	 * 		<tr>
	 * 			<td align="left" valign="top">Real types (float, double) and their wrappers</td>
	 * 			<td align="left" valign="top">JavaScript float.</td>
	 * 		</tr>
	 * 		<tr>
	 * 			<td align="left" valign="top">java.util.Date</td>
	 * 			<td align="left" valign="top">JavaScript string mathing the pattern '????'</td>
	 * 		</tr>
	 * 		<tr>
	 * 			<td align="left" valign="top">java.math.BigDecimal</td>
	 * 			<td align="left" valign="top">JavaScript number (integer or float).</td>
	 * 		</tr>	
	 * 		<tr>
	 * 			<td align="left" valign="top">Arrays and instances of <code>java.util.List</code> or <code>java.util.Collection</code></td> 
	 * 			<td align="left" valign="top">JavaScript array</br>It recursively applies this method to each element in the aforementioned structures.</td>
	 * 		</tr>
	 * 		<tr>
	 * 			<td align="left" valign="top">Instances of <code>java.util.Map</code></td>
	 * 			<td align="left" valign="top">JavaScript hash</br>It recursively applies this method to each element in the aforementioned structures.</td>
	 * 		</tr>
	 * </table> 
	 * @param bean A reference of any type. May be null.
	 * @param writer A non null reference to a <code>java.io.Writer</code> instance.
	 */
	public void encode(Object bean, PrintWriter writer)throws JSONEncodingException {
		Map<String,Map<String,Method>> classesCache = new HashMap<String,Map<String,Method>>();
		try {
			encode(bean, writer, classesCache);
		} catch (IOException e) {
			throw new JSONEncodingException(e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new JSONEncodingException(e);			
		} catch (IllegalAccessException e) {
			throw new JSONEncodingException(e);
		} catch (InvocationTargetException e) {
			throw new JSONEncodingException(e);		}
	}

	public String encode(Object bean)throws JSONEncodingException {
		StringBuilder string = new StringBuilder();
		encode(bean, string);
		return string.toString();
	}
	
	/**
	 *<p>Test whether <code>bean</code> represents an atomic (java.lang.String, java.util.Date, a primitive or a primitive wrapper) value</p>
	 * @param bean Non null object reference.
	 * @return Return <code>true</code> if <code>bean</code> is an atomic valuje.
	 */
	protected boolean isAtomic(Object bean){
		Class<?> clazz = bean.getClass();
		return JSONUtils.isAtomic(clazz);
		/* *
		return bean instanceof String ||
			bean instanceof java.util.Date ||
			clazz.isPrimitive() ||
			Character.class.isAssignableFrom(clazz) ||
			Boolean.class.isAssignableFrom(clazz) ||
			Byte.class.isAssignableFrom(clazz) ||
			Short.class.isAssignableFrom(clazz) ||
			Integer.class.isAssignableFrom(clazz) ||
			Long.class.isAssignableFrom(clazz) ||
			Float.class.isAssignableFrom(clazz) ||
			Double.class.isAssignableFrom(clazz) ||
			BigDecimal.class.isAssignableFrom(clazz) ||
			Enum.class.isAssignableFrom(clazz);
		/* */
	}
	

	
	/**
	 *<p>This method outputs <code>value></code> into <code>writer></code>.</p>
	 * @param value An non atomic value
	 * @param writer A non null writer object.
	 * @throws IOException 
	 */
	protected void print(Object value, PrintWriter writer) throws IOException{
		boolean isQuoted = false;
		String string = NULL;
		
		if(value != null){
			if(value instanceof java.sql.Time){
				string = JSONParser.TIME_PARSER_24H.format(value);
				isQuoted = true;				
			}
			if(value instanceof java.util.Date){				
				string = JSONParser.LONG_DATE_PARSER_AM_PM.format(value);
				isQuoted = true;
			}if(value instanceof java.lang.Enum){
				isQuoted = true;
				string = value.toString();
			}else if(value instanceof String){
				if(((String)value).length() > 0){
					string = (String)value;
					isQuoted = true;
				}else{
					value = NULL;
				}
			}
				
		}else{
			value = NULL;
		}
		
		if(isQuoted){
			JSONEncoder.encode(string, writer);
		}else{
			writer.write(value.toString());
		}
		
	}


	/**
	 *<p>Tries to retrieve a <code>Map<String,Method></code> instance from <code>classesCache</code>; if the trial fails then it creates a new entry in <code>classesCache</code> that will contain all getters method in <code>clazz</code>.</p>
	 * @param clazz .-
	 * @param classesCache .-
	 * @return .-
	 */
	Map<String,Method> getters(Class<?> clazz, Map<String,Map<String,Method>> classesCache){
		Map<String,Method> getters = classesCache.get(clazz.getName());
		String propertyName;
		
		if(getters == null){				
			Method methods[] = clazz.getMethods();				
			String methodName;
			boolean get;				
			StringBuilder propertyNameBuilder = new StringBuilder();
			int start, length, length2;
			char c;
			char lastCharacter=' ';
			
			length = methods.length;				
			getters = new HashMap<String,Method>();
			classesCache.put(clazz.getName(), getters);
			
			for(int i = 0; i < length; i++){
				methodName = methods[i].getName(); 
				if(((get = methodName.startsWith("get")) ||  methodName.startsWith("is")) &&
				   ((methods[i].getModifiers() & Modifier.PUBLIC) > 0) && methods[i].getParameterTypes().length==0 && isIncluded(methods[i])){
					
					start = get ? 3 : 2;
					propertyNameBuilder.append(methodName.substring(start));
					length2 = propertyNameBuilder.length();
					start=0;
					
					while(start < (length2-1) && Character.isUpperCase(c = propertyNameBuilder.charAt(start))){
						propertyNameBuilder.setCharAt(start, Character.toLowerCase(c));
						start++;
						lastCharacter = c;
					}
					
					if(start > 1){
						propertyNameBuilder.setCharAt(start-1, lastCharacter);
					}
					
					propertyName = propertyNameBuilder.toString();
					getters.put(propertyName, methods[i]);
					propertyNameBuilder.delete(0, propertyNameBuilder.length());
				} 			
			}
			
			getters.remove("class");
		}
		
		return getters;
	}

	/**
	 *<p>This method outputs the value of <code>bean</code>'s property whose name matches the string referenced by <code>propertyName</code>.</p> 
	 * @param propertyName Property we want to output.
	 * @param bean A reference of any type. May be null.
	 * @param writer A non null reference to a <code>java.io.Writer</code> instance.
	 * @param classesCache This class is a bare cache containing all getter methods that has been invoked so far.
	 * @param getters Getter methods map.
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws JSONEncodingException 
	 */
	void encode(String propertyName, Object bean, PrintWriter writer, Map<String,Map<String,Method>> classesCache, Map<String,Method> getters) throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, JSONEncodingException{
		Method method = getters.get(propertyName);
		Object value = null;
		writer.write('"');
		writer.write(propertyName);
		writer.write("\":");
		
		//TODO: Encode class name.
		value = method.invoke(bean);			
	
		this.encode(value, writer, classesCache);									
	}
	
	/**
 *<p>This method outputs the value of <code>bean</code>'s property whose name matches the string referenced by <code>propertyName</code>.</p> 
	 * @param key Object that's used as retrieval key for getting <code>bean</code>'s property we want to output.
	 * @param bean A reference of any type. May be null.
	 * @param writer A non null reference to a <code>java.io.Writer</code> instance.
	 * @param classesCache This class is a bare cache containing all getter methods that has been invoked so far.
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws JSONEncodingException 
	 */
	void encode(Object key, Map<?,?> bean, PrintWriter writer, Map<String,Map<String,Method>> classesCache) throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, JSONEncodingException{
		Object value = bean.get(key);
		writer.write('"');
		writer.write(key.toString());
		writer.write("\":");
		this.encode(value, writer, classesCache);									
	}
	
	protected void addClassInfo(Class<?> clazz, PrintWriter writer) throws IOException{
		/* */
		writer.printf("\"%s\":\"%s\"", this.javaClassName, clazz.getName());
		/* */
	}
	
	/**
	 * <p></p>
	 * @param bean
	 * @param writer
	 * @param classesCache
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 * @throws JSONEncodingException 
	 */
	void encodeBean(Object bean, PrintWriter writer, Map<String,Map<String,Method>> classesCache) throws JSONEncodingException, IllegalArgumentException, IOException, IllegalAccessException, InvocationTargetException{
		Map<String,Method> getters = getters(bean.getClass(), classesCache);
		Set<String> keys = getters.keySet();			
		Iterator<String> keysIterator = keys.iterator();
		int length;
		String propertyName;
		
		length = keys.size();
		
		if(length > 0){
			for(int i = 0; i < length -1; i++){
				propertyName = keysIterator.next();
				encode(propertyName, bean, writer, classesCache, getters);
				writer.write(',');
			}
			
			encode(propertyName= keysIterator.next(), bean, writer, classesCache, getters);
		}
	}
	/**
	 *<p>This method is who actually performs the translation when called by <code>BeanHiearchy2JSONEncoder.encode</code>.</p>
	 * @param bean A reference of any type. May be null.
	 * @param writer A non null reference to a <code>java.io.Writer</code> instance.
	 * @param classesCache This class is a bare cache containing all getter methods that has been invoked so far.
	 * @throws IOException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws JSONEncodingException 
	 */
	void encode(Object bean, PrintWriter writer, Map<String,Map<String,Method>> classesCache) throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, JSONEncodingException{
		
		int length;
		Iterator<?> iterator;
		Bean2JSONEncoderDelegate delegate;
		
		
		if(bean == null){
			writer.write(NULL);
		}else if(isAtomic(bean)){
			print(bean, writer);
		}else if ((delegate = delegates.get(bean.getClass().getName()))!=null){
			delegate.encode(bean, writer);
		}else  if(bean instanceof Map<?,?>){
			Map<?,?> map = (Map<?,?>)bean;
			Set<?> keys = map.keySet();
			iterator = keys.iterator();
			length = keys.size();
			Object key;
			writer.write('{');
			if(length > 0){
				for(int i = 0; i < length-1; i++){
					key = iterator.next();
					encode(key, map, writer, classesCache);
					writer.write(',');				
				}
				encode(key = iterator.next(), map, writer, classesCache);
			}			
			writer.write('}');
		}else if(bean instanceof Collection<?>){
			Collection<?> collection = (Collection<?>)bean;
			
			iterator = collection.iterator();			
			length = collection.size();			
			writer.write('[');			
			if(length > 0){
				for(int i = 0; i < length -1; i++){
					encode(iterator.next(), writer, classesCache);
					writer.write(',');
				}
				encode(iterator.next(), writer, classesCache);
			}
			
			writer.write(']');
			
		}else if(bean.getClass().isArray()){
			Object[] array = (Object[])bean;
			length = array.length;
			
			writer.write('[');
			if(length > 0){
				for(int i = 0; i < length -1; i++){
					encode(array[i], writer, classesCache);
					writer.write(',');
				}
				encode(array[length-1], writer, classesCache);
			}
			
			writer.write(']');
		} else{
			if(JSONUtils.isEncodeClass(bean.getClass())){
				writer.write('<');
				writer.write(bean.getClass().getName());
				writer.write('>');
			}			
			writer.write('{');
			encodeBean(bean, writer, classesCache);
			writer.write('}');	
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void encode(Object bean, StringBuilder string)
			throws JSONEncodingException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		PrintWriter writer = new PrintWriter(output);		
		encode(bean, writer);
		writer.flush();
		try {
			output.flush();
			string.append(output.toString());
		} catch (IOException e) {
			throw new JSONEncodingException(e);
		}
		
	}

	

	/**
	 * Returns name of the extra field element to each object (javascript hash) to indicate the java class of the encoded object.
	 * @return .-
	 */
	public String getJavaClassMetaData() {
		return javaClassMetaData;
	}

	/**
	 * Sets name of the extra field element to each object (javascript hash) to indicate the java class of the encoded object.
	 * @param classNameAttribute Non null character string 
	 */
	public void setJavaClassMetaData(String classNameAttribute) {
		this.javaClassMetaData = classNameAttribute;
	}

	/**
	 * Returns the name of the field that contains the full qualified class name of the encoded object.
	 * @return .-
	 */
	public String getJavaClassName() {
		return javaClassName;
	}

	/**
	 * Sets the name of the field that contains the full qualified class name of the encoded object.
	 * @param javaClassName .-
	 */
	public void setJavaClassName(String javaClassName) {
		this.javaClassName = javaClassName;
	}

	/**
	 * Returns the name of the array field that contains all generic types bound to the class of the encoded object (specified by javaClassMetaData).
	 * @return .-
	 */
	public String getJavaGenerics() {
		return javaGenerics;
	}

	/**
	 * Sets the name of the array field that contains all generic types bound to the class of the encoded object (specified by javaClassMetaData).
	 * @param javaGenerics .-
	 */
	public void setJavaGenerics(String javaGenerics) {
		this.javaGenerics = javaGenerics;
	}

	/**
	 *<p>Registers/Unregister an encoder delegate.</p>
	 * @param clazz Class name whose instances translation will be delegated to <code>delegate</code>
	 * @param delegate <code>null</code> if one wants to remove the existing instance bound to <code>clazz</code>.
	 */
	public void registerEncoderDelegate(Class<?> clazz, Bean2JSONEncoderDelegate delegate){
		if(delegate == null){
			delegates.remove(clazz.getName());
		}else{
			delegates.put(clazz.getName(), delegate);
		}
	}

	
	/**
	 *<p>Equivalent to <code>this.registerEncoderDelegate(delegate.getManagedClass(), delegate)</code>.</p>
	 * @param delegate <code>null</code> if one wants to remove the existing instance bound to <code>clazz</code>.
	 */
	public void registerEncoderDelegate(Bean2JSONEncoderDelegate delegate){
		this.registerEncoderDelegate(delegate.getManagedClass(), delegate);
	}

	/**
	 * 
	 * @return Singleton i nstance
	 */
	public static Bean2JSONEncoder getInstance() {
		return instance;
	}	
	
	
}
