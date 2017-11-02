package com.souschef.json.parser.bean;

import static com.souschef.json.JSONUtils.isIncluded;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.souschef.json.JSONUtils;
import com.souschef.json.parser.JSONParser;
import com.souschef.json.parser.JSONParserContext;

/**
 *<p>This class provides context information to <code>com.castlebreck.json.parse.JSON2BeanHierarchyParserEventListener</code> instances.</p>
 *<p>Copyright &copy; 1998 - 2010 <a href="http://www.castlebreck.com">Castlebreck Inc.</a>  All rights reserved.  This software may only be copied, altered, transferred or used in accordance<br/>
 *with the Castlebreck General Services Agreement which is available upon request from Castlebreck Inc. Visit www.castlebreck.com for<br/>
 *contact information.  This notice may not be removed and must be included with any copies of this work.</p>
 * @author rsolano
 *
 */
public class JSON2BeanParserContext extends JSONParserContext{
	/**
	 *<p>Used internally for creating delegates.</p>
	 * @author rsolano
	 *
	 */
    static interface PushMethodDelegate
    {
    	/**
    	 * Pushes <code>element</code> into the parsing stack.
    	 * @param element .-
    	 */
        public abstract void push(JSON2BeanStackElement element);
    }
	
    /**
     * <p>Instances of this classes contain a map of setter methods and a reference to the class those methods belong to.</p>
     *
     */
    static class ClassMetaInfo{
    	
    	/**
    	 * <p>Setter methods map; indexed by property name.</p>
    	 */
    	Map<String,Method> setters;
    	
    	/**
    	 * Class that contains the setter methods stored in the above map.
    	 */
    	Class<?> clazz;
    	
    	/**
    	 * Initializes declared field whose names match parameters.
    	 * @param clazz
    	 * @param setters
    	 */
    	ClassMetaInfo(Class<?> clazz, Map<String,Method> setters){
    		this.clazz = clazz;
    		this.setters = setters;
    	}
    }
    
    
    /**
     * <p>When <code>true</code> indicates that this context delegating event processing to some <code>com.nemesys.json.parser.JSON2BeanHierarchyParserEvent</code>.</p>
     */
    private boolean customProcessing=false;
    
	/**
	 * <p>Event processing is delegated to this object <code>JSONParserEventListener</code> the element in parsing stack need is of a class has been registered for special processing.</p> 
	 */
	private JSON2BeanParserEvent parserEventListenerDelegate;
	
    /**
     *<p>Classes loaded so far in the translation process.</p>
     */
    Map<String,ClassMetaInfo> loadedClasses = new HashMap<String,ClassMetaInfo>();
    

    /**
     *<p>Customized event handlers; customized event handlers provide hints for handling odd classes (those whose properties do not follow the JavaBeans standard).</p>
     */
    Map<String,JSON2BeanParserEvent> parserEvents;
    
    /**
 	*<p>parsing stack's root element</p>
     */
    JSON2BeanStackElement root;
	
	/**
	 *<p>parsing stack's top element.</p>
	 */
    JSON2BeanStackElement top;
	
	/**
	 	*<p>parsing stack</p>
	 */
	Stack<JSON2BeanStackElement> stack = new Stack<JSON2BeanStackElement>();	

	
	/**
	 *	This boolean flags indicates whether initial initialization was accomplished. 
	 */
	boolean initialized;
	
	/**
	 * Pushes <code>root</code> into the parsing stack.
	 * @param root
	 */
	public JSON2BeanParserContext(Object root){
		pushDelegate.push(new JSON2BeanStackElement(root, null));
	}
	
	/**
	 * 
	 * @param valueType
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 */
	public JSON2BeanParserContext(Class<?> valueType) throws IllegalAccessException, InstantiationException{
		
		if(!JSONUtils.isAtomic(valueType)){ 
			pushDelegate.push(new JSON2BeanStackElement(valueType.newInstance(), null));
		}
	}
	
	/**
	 * <p>Creates  meta info object from <code>clazz</code></p>
	 * @param clazz
	 * @return
	 */
	protected ClassMetaInfo loadClassMetaInfo(Class<?> clazz ){
		ClassMetaInfo metaInfo = loadedClasses.get(clazz.getName());
		
		if(metaInfo == null){
			Map<String,Method> setters = new HashMap<String,Method>();
			setters = new HashMap<String,Method>();
			Method methods[] = clazz.getMethods();				
			String methodName;
			StringBuilder propertyNameBuilder = new StringBuilder();
			int start, length, length2;
			char c;
			char lastCharacter=' ';
			String propertyName;
			length = methods.length;
			
			for(int i = 0; i < length; i++){
				methodName = methods[i].getName(); 
				if((methodName.startsWith("set") && methods[i].getParameterTypes().length==1) && ((methods[i].getModifiers() & Modifier.PUBLIC) > 0) && isIncluded(methods[i])){
					
					start = 3;
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
					setters.put(propertyName, methods[i]);
					propertyNameBuilder.delete(0, propertyNameBuilder.length());
				} 			
			}
			
			metaInfo = new ClassMetaInfo(clazz, setters);
			loadedClasses.put(clazz.getName(), metaInfo);
		}		
		
		return metaInfo;
	}
	protected ClassMetaInfo loadClassMetaInfo(Object bean){
		Class<?> clazz = bean.getClass();
		return loadClassMetaInfo(clazz);
	}
	
	protected ClassMetaInfo loadClassMetaInfo(String className) throws ClassNotFoundException{
		ClassMetaInfo metaInfo = loadedClasses.get(className);
		if(metaInfo == null){
			metaInfo = loadClassMetaInfo(Class.forName(className));
		}
		return metaInfo;
	}
	
	/**
	 * <p>This delegate perform the 1st push operation.</p>
	 */
	PushMethodDelegate furtherPushsDelegates = new PushMethodDelegate() {

		/**
		 * {@inheritDoc}
		 */
        public void push(JSON2BeanStackElement element)
        {
            stack.push(element);
            top = element;
            loadClassMetaInfo(element.getObject());
        }            
    };
    
    /**
     * <p>This delegate perform the push operation after 1st one.</p>
     */
    PushMethodDelegate initialPushDelegate = new PushMethodDelegate() {

    	/**
    	 * {@inheritDoc}
    	 */
        public void push(JSON2BeanStackElement element)
        {
            root = element;
            stack.push(element);
            pushDelegate = furtherPushsDelegates;
            top = element;
            loadClassMetaInfo(element.getObject());
        }
    };
    
    /**
     * 
     */
    PushMethodDelegate pushDelegate = initialPushDelegate;
    
    /**
     *<p>Returns the structured element that's currently being buit.</p>
     * @return .-
     */
    public Object getTop()
    {
        return top.getObject();
    }

    /**
     *<p>Returns the structured element that's the root of the hierarchy being parsed.</p>
     * @return
     */
    public Object getRoot()
    {
        return root.getObject();
    }	

    
    
    /**
     * <code>Push an element to the internal stack.</code>
     * @param element Element to be pushed.
     * @param name If not null, it must be a non empty string.
     */
    public void push(Object element, String name)
    {
        pushDelegate.push(new JSON2BeanStackElement(element,name));
    }

    /**
     * <code>Push an element to the internal stack.</code>
     * @param element Element to be pushed.
     */
    public void push(Object element)
    {
        pushDelegate.push(new JSON2BeanStackElement(element,null));
    }    
    
          
    /**
     * Utility method for throwing exceptions when a property can't be set to its designated value.
     * @param name Property name
     * @param value Designated value
     * @param setter Setter method
     * @param metaInfo Class meta info
     * @throws ParseException
     */
    private void throwAssignmentException(String name, Object value, Method setter, ClassMetaInfo metaInfo) throws ParseException{	    	
    	throw new ParseException("Can't assign "+value+" to property '"+name+"' ("+setter.getParameterTypes()[0].getClass().getName()+") in "+metaInfo.clazz.getName(), -1);
    }
    
    
    /**
     * <p>This method sets the value of the property identified by <code>name</code>. This method assumes that <code>this.top</code> is a POJO or <code>java.util.Map</code> instance (neither <code>java.util.List</code>  nor <code>java.util.Collection</code>). </p>
     * @param name Property name
     * @param value Property value
     * @throws ParseException
     */
    public void setProperty(String name, Object value) throws ParseException {
    	Object top = this.top.getObject();
    	if(!ignore){
    		if(top != null){
		    	if(top instanceof java.util.Map){
		    		setMapProperty(name, value);
		    	}else if(top instanceof java.util.Collection){
	    			addPropertyToCollection(value);
		    	}else{
		    		setPOJOProperty(name, value);
		    	}
    		}else{ //This block gets executed when value an atomic value.
    			root = new JSON2BeanStackElement(value, null);
    		}
    	}
    }
    
    /**
     * <p>This method adds a value to <code>java.util.List&lt;?&gt;</code> instance stored in <code>this.top</code>; it  assumes that <code>this.top</code> points to a typed implementation of <code>java.util.List&lt;?&gt;</code>.</p>
     * <p>The difference between typed and untyped implentantion is better understood by watching the code snipets below: </p>
     * <p>Example 1 (untyped map declaration): <br/>
     * <code>
     *  <font color="green">//Generic parameters information is lost due to type erasure.</font><br/>
     * 	<b>List&lt;Integer&gt;</b> integerList = new <b>ArrayList&lt;Integer&gt;()</b>;
     * </code>
     * </p>
     * <p>Example 1 (typed map declaration): <br/>
     * <code>
     * <font color="green">//integerList is a typed list since it provides implementations <br/>//for the generic parameters that java.util.List expects.</font><br/>
     *  public class <b>IntegerList</b> extends </b>ArrayList&lt;Integer&gt;(){}<br/>
     *  .</br>
     *  .</br>
     *  .</br>
     *  
     *  <font color="green">//Generic parameters information is not<br/>// lost because it's preserved by the compiler.</font></br>
     * 	<b>IntegerList</b> integerList = new <b>IntegerList()</b>;
     * </code>
     * </p>
     * @param value Property value
     * @throws ParseException 
     */
    @SuppressWarnings("unchecked")
	protected void addPropertyToCollection(Object value) throws ParseException{
    	Object top = this.top.getObject();
    	Collection<Object> list = (Collection<Object>) top;
    	Type valueType= ((ParameterizedType)list.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    	String valueTypeName = valueType.toString();
    	int pos = valueTypeName.indexOf(' ')+1;
    	valueTypeName = pos > 0 ? valueTypeName.substring(pos): "java.lang.Object";
    	Class<?> clazz;	    	

    	try {
			clazz = Class.forName(valueTypeName);
	    	if(Byte.class.isAssignableFrom(clazz) || byte.class.isAssignableFrom(clazz)){
	    		list.add(((Number)value).byteValue());
	    	}else if(Short.class.isAssignableFrom(clazz) || short.class.isAssignableFrom(clazz)){	
	    		list.add(((Number)value).shortValue());
	    	}else if(Integer.class.isAssignableFrom(clazz)  || int.class.isAssignableFrom(clazz)){
	    		list.add(((Number)value).intValue());
	    	}else if(Long.class.isAssignableFrom(clazz) || long.class.isAssignableFrom(clazz)){
	    		list.add(((Number)value).longValue());
	    	}else if(Float.class.isAssignableFrom(clazz) || float.class.isAssignableFrom(clazz) ){
	    		list.add(((Number)value).floatValue());
	    	}else if(Double.class.isAssignableFrom(clazz) || double.class.isAssignableFrom(clazz) ){
	    		list.add(((Number)value).doubleValue());
	    	}else if(BigDecimal.class.isAssignableFrom(clazz) ){
	    		if(value instanceof BigDecimal){
	    			list.add((BigDecimal)value);
	    		}else{
	    			list.add( new BigDecimal(value.toString()));
	    		}
	    	}else{
	    		if(pos > 0 && value != null && !clazz.isAssignableFrom(value.getClass()))
	    			throw new ClassCastException(list.getClass().getName()+" lists don't hold instances of "+value.getClass().getName());
	    		list.add(value);
	    	}				
		} catch (ClassNotFoundException e) {
			throw new ParseException("Can't load "+list.getClass().getName()+" class needed for creating adding "+value+" to " +top.getClass().getName()+ " of type "+value.getClass().getName(), -1);
		}	    	
    }
    
    /**
     * <p>This method sets the value of the property identified by <code>name</code>; it  assumes that <code>this.top</code> points to a typed implementation of <code>java.util.Map&lt;String,?&gt;</code>.</p>
     * <p>The difference between typed and untyped implentantion is better understood by watching the code snipets below: </p>
     * <p>Example 1 (untyped map declaration): <br/>
     * <code>
     *  <font color="green">//Generic parameters information is lost due to type erasure.</font><br/>
     * 	<b>Map&lt;String,Integer&gt;</b> integerMap = new <b>HashMap&lt;String,Integer&gt;()</b>;
     * </code>
     * </p>
     * <p>Example 1 (typed map declaration): <br/>
     * <code>
     * <font color="green">//IntegerMap is a typed map since it provides implementations <br/>//for the generic parameters that java.util.HashMap expects.</font><br/>
     *  public class <b>IntegerMap</b> extends </b>HashMap&lt;String,Integer&gt;</b>(){}<br/>
     *  .</br>
     *  .</br>
     *  .</br>
     *  
     *  <font color="green">//Generic parameters information is not<br/>// lost because it's preserved by the compiler.</font></br>
     * 	<b>IntegerMap</b> integerMap = new <b>IntegerMap()</b>;
     * </code>
     * </p>
     * @param name Property name
     * @param value Property value
     * @throws ParseException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	protected void setMapProperty(String name, Object value) throws ParseException {   	
    	String string;
    	Object top = this.top.getObject();
    	Map<String,Object> map = (Map<String, Object>) top;
	    	
    	Type valueType= ((ParameterizedType)map.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    	String valueTypeName = valueType.toString();
    	int pos = valueTypeName.indexOf(' ')+1;
    	valueTypeName = pos > 0 ? valueTypeName.substring(pos): "java.lang.Object";	    	
		    	
    	Class<?> clazz=null;
		    	
    	try {		    		
			clazz = Class.forName(valueTypeName);
			if(Number.class.isAssignableFrom(clazz) && value instanceof String){
				string = value.toString().trim();
				value = JSONParser.toNumber(string);
				if(value == null){
					throw new NumberFormatException('\''+string+"' is not a string that represents a number and this map ("+map.getClass().getName()+") only accepts numeric values.");
				}
		   	}
					
			if(Enum.class.isAssignableFrom(clazz)){
    			map.put(name, Enum.valueOf((Class)clazz, value.toString()));
    		}else if(Boolean.class.isAssignableFrom(clazz) || boolean.class.isAssignableFrom(clazz)){
				map.put(name, ((Boolean)value).booleanValue());
			}else if(Byte.class.isAssignableFrom(clazz) || byte.class.isAssignableFrom(clazz)){
		   		map.put(name, ((Number)value).byteValue());
		   	}else if(Short.class.isAssignableFrom(clazz) || short.class.isAssignableFrom(clazz)){	
		    	map.put(name, ((Number)value).shortValue());
		   	}else if(Integer.class.isAssignableFrom(clazz)  || int.class.isAssignableFrom(clazz)){
		    	map.put(name, ((Number)value).intValue());
		   	}else if(Long.class.isAssignableFrom(clazz) || long.class.isAssignableFrom(clazz)){
		   		map.put(name, ((Number)value).longValue());
		   	}else if(Float.class.isAssignableFrom(clazz) || float.class.isAssignableFrom(clazz) ){
		   		map.put(name, ((Number)value).floatValue());
		   	}else if(Double.class.isAssignableFrom(clazz) || double.class.isAssignableFrom(clazz) ){
		    	map.put(name, ((Number)value).doubleValue());
		   	}else if(BigDecimal.class.isAssignableFrom(clazz)){
		   		if(value instanceof BigDecimal){
		   			map.put(name, (BigDecimal)value);
		   		}else{
		   			map.put(name, new BigDecimal(value.toString()));
		    	}
		   	}else {
	    		if(pos > 0 && value != null && !clazz.isAssignableFrom(value.getClass()))
	    			throw new ClassCastException(map.getClass().getName()+" maps don't hold instances of "+value.getClass().getName());			   		
		    	map.put(name, value);
		   	}				
		} catch (ClassNotFoundException e) {
			throw new ParseException("Can't load "+valueTypeName+" class needed for creating  "+name+" property for  map of type "+map.getClass().getName(), -1);
		}
	    	
    }
    
    
    /**
     * <p>This method sets the value of the property identified by <code>name</code>. This method assumes that <code>this.top</code> is a POJO.</p>
     * @param name Property name
     * @param value Property value
     * @throws ParseException
     */	    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void setPOJOProperty(String name, Object value) throws ParseException{
		Object top = this.top.getObject();
    	ClassMetaInfo metaInfo = loadedClasses.get(top.getClass().getName());
    	Method setter = metaInfo.setters.get(name);	  	    	
    	Class<?> parameterType;
    	String string;
    	
  
    	if(setter != null){
    		parameterType = setter.getParameterTypes()[0];
			if(Number.class.isAssignableFrom(JSONUtils.wrap(parameterType)) && value instanceof String){
				string = value.toString().trim();
				value = JSONParser.toNumber(string);
				if(value == null){
					throw new NumberFormatException('\''+string+"' is not a string that represents a number and this setter method ("+top.getClass().getName()+'.'+setter.getName()+") only accepts numeric values.");
				}
	    	}
			
	    	try{
	    		if(Enum.class.isAssignableFrom(parameterType)){
	    			setter.invoke(top, Enum.valueOf((Class)parameterType, value.toString()));
	    		}else if(Boolean.class.isAssignableFrom(parameterType) || boolean.class.isAssignableFrom(parameterType)){
					setter.invoke(top, ((Boolean)value).booleanValue());
				}else if(Byte.class.isAssignableFrom(parameterType) || byte.class.isAssignableFrom(parameterType)){
		    		setter.invoke(top, ((Number)value).byteValue());
		    	}else if(Short.class.isAssignableFrom(parameterType) || short.class.isAssignableFrom(parameterType)){	
		    		setter.invoke(top, ((Number)value).shortValue());
		    	}else if(Integer.class.isAssignableFrom(parameterType) || int.class.isAssignableFrom(parameterType)){
		    		setter.invoke(top, ((Number)value).intValue());
		    	}else if(Long.class.isAssignableFrom(parameterType)  || long.class.isAssignableFrom(parameterType)){
		    		setter.invoke(top, ((Number)value).longValue());
		    	}else if(Float.class.isAssignableFrom(parameterType)  || float.class.isAssignableFrom(parameterType)){
		    		setter.invoke(top, ((Number)value).floatValue());
		    	}else if(Double.class.isAssignableFrom(parameterType)  || double.class.isAssignableFrom(parameterType)){
		    		setter.invoke(top, ((Number)value).doubleValue());
		    	}else if(BigDecimal.class.isAssignableFrom(parameterType)){
		    		if(value instanceof BigDecimal){
		    			setter.invoke(top, (BigDecimal)value);
		    		}else{
		    			setter.invoke(top, new BigDecimal(value.toString()));
		    		}
		    	}else{
		    		setter.invoke(top, value);
		    	}
	    	}catch(NullPointerException e){	    		
	    		throwAssignmentException(name, value, setter, metaInfo);
	    	} catch (IllegalArgumentException e) {
	    		throwAssignmentException(name, value, setter, metaInfo);
			} catch (IllegalAccessException e) {
				throwAssignmentException(name, value, setter, metaInfo);
			} catch (InvocationTargetException e) {
				throwAssignmentException(name, value, setter, metaInfo);
			}
    	}
    }
    
    
    Stack<Object> structureStack = new Stack<Object>();
    
    private Object createInstance(Class<?> parameterType) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
    	return getObjectClass() == null ? parameterType.getConstructor().newInstance() : getObjectClass().newInstance();
    }
    /**
     * <p>Creates a new object that can be used as argument for <code>this.top</code>'s setter method bound to <code>name</code> string in meta info's <code>setters</code> map.</p>
     * @param name Property name, non null/empty string
     * @return .-
     * @throws ParseException
     */
	protected Object createInstance(String name) throws ParseException{
		Object top = this.top.getObject();
    	ClassMetaInfo metaInfo = loadedClasses.get(top.getClass().getName());
    	Method setter = metaInfo.setters.get(name);
    	Object object = null;
    	Class<?> parameterType=null;
    	Collection<?> list;
    	Map<?,?> map;
    	
    	Type valueType;	    	
    	String valueTypeName;
    	int pos;
    	
    	try {
    		
    		if (top instanceof Map){
	    		map = (Map<?,?>) top;
		    	valueType= ((ParameterizedType)map.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		    	
		    	//TODO:Refactor into function createAggregatedObject
		    	valueTypeName = valueType.toString();
		    	pos = valueTypeName.indexOf(' ')+1;
		    	valueTypeName = pos > 0 ? valueTypeName.substring(pos): "java.util.HashMap";	    	
		    	parameterType = loadClassMetaInfo(valueTypeName).clazz;
		    	object = createInstance(parameterType);
		    	/* */
	    	}else if (top instanceof Collection){
	    		list = (Collection<?>) top;
	    		valueType= ((ParameterizedType)list.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	    		
	    		//TODO:Refactor into function createAggregatedObject
	    		valueTypeName = valueType.toString();
	    		pos = valueTypeName.indexOf(' ')+1;
	    		valueTypeName = pos > 0 ? valueTypeName.substring(pos): "java.util.HashMap";
	    		parameterType = loadClassMetaInfo(valueTypeName).clazz;
	    		object = createInstance(parameterType);
	    		/* */
	    		
	    	}else if(name != null){
	    		setter = metaInfo.setters.get(name);
	    		if(setter != null){
	    			parameterType = setter.getParameterTypes()[0];		
	    			object = createInstance(parameterType);	    			
		    	}else{
		    		ignore = true;
		    	}
    		}
    		
    		
    		if(object instanceof Collection || object instanceof Map){
    			structureStack.push(object);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
			throw new ParseException("Cant create value for property '"+name+"' ("+parameterType+") in "+top.getClass().getName(),-1);
		}
    	setObjectClass(null);
    	return object;
    }
    
    /**
     * <p>The parser neither push  elements into the stack nor assign values to properties  while this flag is set to  <code>true</code></p>
     */
    protected boolean ignore=false;
    
    /**
     * <p>Creates a new object that can be used as argument for <code>this.top</code>'s setter method bound to <code>name</code> string in meta info's <code>setters</code> map; upon creation,
     * the new object is pushed into the parsing stack and thus becomes <code>top</code></p>
     * @param name
     * @throws ParseException 
     */
    public void push(String name) throws ParseException{
    	Object element=null;
    	element = createInstance(name);
	    if(element != null){
	    	/*
		   	setProperty(name, element);
		   	*/
		   	push(element, name);		    	
	    }	
    	
    }
    
    /**
     *<p>Pops the last element out of the internal stack.</p>
     * @return The last element in the internal stack.
     * @throws ParseException 
     */
    public Object pop() throws ParseException
    {
    	JSON2BeanStackElement top = null;
        if(stack.size() > 0){
        	top = stack.pop();        	
        	if(stack.size() > 0){
        		this.top = stack.lastElement();
        		setProperty(top.getName(), top.getObject());
        	}else
	            this.top = root;
        }
        else
            this.top = root;
        ignore = false;
        
   		if(structureStack.size() > 0 && (top instanceof Collection || top instanceof Map)){
   			structureStack.pop();
		}
        return top;
    }

    /**
     * @return  <code>true</code> indicates that this context delegating event processing to some com.nemesys.json.parser.JSON2BeanHierarchyParserEvent.
     */
	public boolean isCustomProcessing() {
		return customProcessing;
	}

	/**
	 * Sets <code>this.customProcessing</code> flag.
	 * @param customProcessing
	 */
	public void setCustomProcessing(boolean customProcessing) {
		this.customProcessing = customProcessing;
	}

	/**
	 * 
	 * @return The current event processing delegate or null if default processing policies are the on eused. 
	 */
	JSON2BeanParserEvent getParserEventListenerDelegate() {
		return parserEventListenerDelegate;
	}

	/**
	 * Sets the event procesing delegate
	 * @param parserEventListenerDelegate .- 
	 */
	void setParserEventListenerDelegate(
			JSON2BeanParserEvent parserEventListenerDelegate) {
		this.parserEventListenerDelegate = parserEventListenerDelegate;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setRoot(Object root) {
		this.root = new JSON2BeanStackElement(root, null);
	}
    
    

}
