package com.souschef.json;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.souschef.util.StringUtil;

/**
 * <p>This class is a set of static methods used horizontally by classes in <code>com.castlebreck.json.*</code> packages</p>
 * @author rsolano
 *
 */
public class JSONUtils {
		/**
		 * Maps primitive classes to their wappers.
		 */
	  private static final Map<Class<?>, Class<?>> PRIMITIVES_TO_WRAPPERS;
	  static {
		  PRIMITIVES_TO_WRAPPERS = new HashMap<Class<?>, Class<?>>();
		  PRIMITIVES_TO_WRAPPERS.put(boolean.class, Boolean.class);
		  PRIMITIVES_TO_WRAPPERS.put(byte.class, Byte.class);
		  PRIMITIVES_TO_WRAPPERS.put(char.class, Character.class);
		  PRIMITIVES_TO_WRAPPERS.put(double.class, Double.class);
		  PRIMITIVES_TO_WRAPPERS.put(float.class, Float.class);
		  PRIMITIVES_TO_WRAPPERS.put(int.class, Integer.class);
		  PRIMITIVES_TO_WRAPPERS.put(long.class, Long.class);
		  PRIMITIVES_TO_WRAPPERS.put(short.class, Short.class);
		  PRIMITIVES_TO_WRAPPERS.put(void.class, Void.class);
	  }
			      
	/**
	 * @param method
	 * @return <code>true</code> if <code>method</code> is not annotated with <code>isExcluded</code>  or the current <code>isExcluded</code>'s value otherwise.  
	 */
	public static boolean isIncluded(Method method){
		Class<?> returnType = method.getReturnType();
		JSONPolicy policy = method.getAnnotation(JSONPolicy.class);
		
		boolean included = (policy == null ? true: !policy.isExcluded()) && 
				(returnType.getName().compareTo(Class.class.getName()) != 0);
		return included;
	}		
	
	/**
	 * @param returnType
	 * @return <code>false</code> if <code>method</code> is not annotated with <code>isEncodeClass</code> or the current <code>isEncodeClass</code>'s value otherwise.  
	 */	
	public static boolean isEncodeClass(Class<?> returnType){
		JSONPolicy policy = returnType.getAnnotation(JSONPolicy.class);
		
		
		boolean encodeClass = (policy == null ? false: policy.isEncodeClass()) && 
				(returnType.getName().compareTo(Class.class.getName()) != 0);
		return encodeClass;		
	}
	
	 @SuppressWarnings("unchecked")
	 /**
	  * 
	  * @param c
	  * @return Returns the a wrapper class if <code>c</code> is a primitive. Otherwise returns null.
	  */
	 public static <T> Class<T> wrap(Class<T> c) {
	    return c.isPrimitive() ? (Class<T>) PRIMITIVES_TO_WRAPPERS.get(c) : c;
	 }
	 
	/**
	 *<p>Test whether <code>clazz</code> represents an atomic (java.lang.String, java.util.Date, a primitive or a primitive wrapper) value</p>
	 * @param clazz Non null object reference.
	 * @return Return <code>true</code> if <code>bean</code> is an atomic valuje.
	 */
	public static boolean isAtomic(Class<?> clazz){
		
		return clazz == String.class ||
			clazz	 == java.sql.Timestamp.class ||
			clazz	 == java.sql.Time.class ||
			clazz	 == java.util.Date.class ||
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
	}	
	
	/**
	 *<p>Test whether <code>clazz</code> a JSON value type</p>
	 * @param clazz Non null object reference.
	 * @return Return <code>true</code> if <code>bean</code> is an atomic valuje.
	 */
	public static Class<?> getJSONValueType(Class<?> clazz){
		Class<?> jsonValueType = null;
		if(clazz == String.class ||
			clazz == java.util.Date.class ||
			Boolean.class.isAssignableFrom(clazz) ) {
			jsonValueType = clazz;
		}else if (Number.class.isAssignableFrom(clazz)){
			jsonValueType = Number.class;
		}
		
		return jsonValueType;
	}	
	
	/**
	 * <p>Compares two dates. This method checks whether either date is null when comparing them.
	 * @param date1 Date value.
	 * @param date2 Date value.
	 * @return
	 */
	public static boolean dateSafeEquals(Date date1, Date date2){
		boolean isEquals = true;
		if(date1 != date2){
			if(!(date1 == null || date2 == null)){				
				isEquals = StringUtil.stringSafeEquals(date1.toString(), date2.toString());
			}else{
				isEquals = false;
			}
		}
		return isEquals;
	}	
}
