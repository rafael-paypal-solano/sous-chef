package com.souschef.json.parser.bean;

import java.text.ParseException;
import java.util.Date;

import com.souschef.json.parser.JSONParserContext;

/**
 * <p>This class provides a way for implementing special decoding logic for structured JSON data. This interface was devised as start point
 * for providing hints to the parser when decoding JSON into objects that are neither JavaBeans, collection nor maps.</p>
 *<p>Copyright &copy; 1998 - 2010 <a href="http://www.castlebreck.com">Castlebreck Inc.</a>  All rights reserved.  This software may only be copied, altered, transferred or used in accordance<br/>
 *with the Castlebreck General Services Agreement which is available upon request from Castlebreck Inc. Visit www.castlebreck.com for<br/>
 *contact information.  This notice may not be removed and must be included with any copies of this work.</p>
 *
 */
public abstract class JSON2BeanParserEvent implements com.souschef.json.parser.JSONParserEventListener{
	
	/**
	 * 
	 */
	Class<?> clazz;
	
	/**
	 * Just initializes <code>this.clazz</code>;
	 * @param clazz
	 */
	public JSON2BeanParserEvent(Class<?> clazz){
		this.clazz = clazz;
	}
	
	/**
	 *<p>This event handler is triggered after parser ends processing an string element.</p>
	 * @param context Parsing context information.
	 * @param name Element name
	 * @param value String content
	 * @throws ParseException .-
	 */
	public abstract void onString(JSON2BeanParserContext context, String name, String value) throws ParseException;
	
	/**
	 *<p>This event handler is triggered after parser ends processing an number element.</p>
	 * @param context Parsing context information.
	 * @param name Element name
	 * @param value Instance of number
	 * @throws ParseException .-
	 */
	public abstract void onNumber(JSON2BeanParserContext context, String name, Number value) throws ParseException;
	
	/**
	 *<p>This event handler is triggered after parser ends processing an boolean element.</p>
	 * @param context Parsing context information.
	 * @param name Element name
	 * @param value <code>true</code> or <code>false</code>
	 * @throws ParseException .-
	 */
	public abstract void onBoolean(JSON2BeanParserContext context, String name, boolean value) throws ParseException;
	
	/**
	 *<p>This event handler is triggered after parser ends processing an <code>null</code> element.</p>
	 * @param context Parsing context information.
	 * @param name <code>true</code> or <code>false</code>
	 * @throws ParseException .-
	 */
	public abstract void onNull(JSON2BeanParserContext context, String name) throws ParseException;
	
	/**
 	*<p>This event handler is triggered after parser ends processing a string that encodes a </code>java.util.Date</code> value.</p>
	 * @param context Parsing context information.
	 * @param name <code>true</code> or <code>false</code>
	 * @param value A non null instance of </code>java.util.Date</code>.
	 * @throws ParseException .-
	 */
	public abstract void onDate(JSON2BeanParserContext context, String name, Date value) throws ParseException;
	
	/**
	 * 
	 * @return The class whose instances can be decoded from JSON.
	 */
	public abstract Class<?> getManagedClass();
	/**
	 * <p>Creates a new instance of <code>clazz</code>.</p>
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	protected Object createInstance() throws IllegalAccessException, InstantiationException{
		return clazz.newInstance();
	}
	/**
	 *<p>This event handler is triggered before parser starts to process a structured element.</p>
	 * @param context Parsing context information.
	 * @param name Element name
	 * @param type Structure type
	 * @return <code>true</code> to stop custom processing.
	 * @throws ParseException .-
	 */
	public abstract boolean onStructureStart(JSON2BeanParserContext context, String name, StructureTypeEnum type) throws ParseException;
	
	/**
	 *<p>This event handler is triggered after parser ends processing a structured element.</p>
	 * @param context Parsing context information.
	 * @param name Element name
	 * @param type Structure type
	 * @return <code>true</code> to stop custom processing.
	 * @throws ParseException .-
	 */
	public abstract boolean onStructureEnd(JSON2BeanParserContext context, String name, StructureTypeEnum type) throws ParseException;

	/**
	 * {@inheritDoc}
	 */
	public void onBoolean(JSONParserContext context, String name, boolean value) throws ParseException {
		this.onBoolean((JSON2BeanParserContext)context, name, value);
	}

	/**
	 * {@inheritDoc}
	 */	
	public void onDate(JSONParserContext context, String name, Date value) throws ParseException {
		this.onDate((JSON2BeanParserContext)context, name, value);
	}

	/**
	 * {@inheritDoc}
	 */	
	public void onNull(JSONParserContext context, String name) throws ParseException {
		this.onNull((JSON2BeanParserContext)context, name);
	}

	/**
	 * {@inheritDoc}
	 */	
	public void onNumber(JSONParserContext context, String name, Number value) throws ParseException {
		this.onNumber((JSON2BeanParserContext)context, name, value);
	}

	/**
	 * {@inheritDoc}
	 */	
	public void onString(JSONParserContext context, String name, String value) throws ParseException {
		this.onString((JSON2BeanParserContext)context, name, value);
	}

	/**
	 * {@inheritDoc}
	 */	
	public void onStructureEnd(JSONParserContext context, String name, StructureTypeEnum type) throws ParseException {
		this.onStructureEnd((JSON2BeanParserContext)context, name, type);
	}

	/**
	 * {@inheritDoc}
	 */	
	public void onStructureStart(JSONParserContext context, String name, StructureTypeEnum type) throws ParseException {
		this.onStructureStart((JSON2BeanParserContext)context, name, type);
	}
	
	
}
