package com.souschef.json.parser;

import java.util.Date;
import java.text.ParseException;

/**
 *<p>This interface allows to implement specific processing to each JSON data type/struture.<p>
 *<p>The parser parses the content and the class implementing this interface does something useful with the parsed data.</p>
 *<p>Copyright &copy; 1998 - 2010 <a href="http://www.castlebreck.com">Castlebreck Inc.</a>  All rights reserved.  This software may only be copied, altered, transferred or used in accordance<br/>
 *with the Castlebreck General Services Agreement which is available upon request from Castlebreck Inc. Visit www.castlebreck.com for<br/>
 *contact information.  This notice may not be removed and must be included with any copies of this work.</p>
 *@author rsolano
 *
 */
public interface JSONParserEventListener {
	
	/**
	 *<p>
	 * JSON is built on two structures:
	 * <ul>
	 * 	A collection of name/value pairs. In various languages, this is realized as an object, record, struct, dictionary, hash table, keyed list, or associative array.
	 * </ul>
	 * <ul>
	 * An ordered list of values. In most languages, this is realized as an array, vector, list, or sequence.
	 * </ul>
	 * </p>
	 * @author root
	 *
	 */
	public enum StructureTypeEnum{
		/**
		 * An ordered list of values. (example: <b>[value1, value2, ...]</b>)
		 */
		ARRAY_TYPE,
		
		/**
		 * A collection of name/value pairs. (example: <b>{"key1": value1, "key2": value2, ...}</b>)
		 */
		MAP_TYPE
	}
	
	/**
	 *<p>This event handler is triggered before parser starts to process a structured element.</p>
	 * @param context Parsing context information.
	 * @param name Element name
	 * @param type Structure type
	 * @throws ParseException .-
	 */
	public void onStructureStart(JSONParserContext context, String name, StructureTypeEnum type) throws ParseException;
	
	/**
	 *<p>This event handler is triggered after parser ends processing a structured element.</p>
	 * @param context Parsing context information.
	 * @param name Element name
	 * @param type Structure type
	 * @throws ParseException .-
	 */
	public void onStructureEnd(JSONParserContext context, String name, StructureTypeEnum type) throws ParseException;
	
	/**
	 *<p>This event handler is triggered after parser ends processing an string element.</p>
	 * @param context Parsing context information.
	 * @param name Element name
	 * @param value String content
	 * @throws ParseException .-
	 */
	public void onString(JSONParserContext context, String name, String value) throws ParseException;
	
	/**
	 *<p>This event handler is triggered after parser ends processing an number element.</p>
	 * @param context Parsing context information.
	 * @param name Element name
	 * @param value Instance of number
	 * @throws ParseException .-
	 */
	public void onNumber(JSONParserContext context, String name, Number value) throws ParseException;
	
	/**
	 *<p>This event handler is triggered after parser ends processing an boolean element.</p>
	 * @param context Parsing context information.
	 * @param name Element name
	 * @param value <code>true</code> or <code>false</code>
	 * @throws ParseException .-
	 */
	public void onBoolean(JSONParserContext context, String name, boolean value) throws ParseException;
	
	/**
	 *<p>This event handler is triggered after parser ends processing an <code>null</code> element.</p>
	 * @param context Parsing context information.
	 * @param name <code>true</code> or <code>false</code>
	 * @throws ParseException .-
	 */
	public void onNull(JSONParserContext context, String name) throws ParseException;
	
	/**
 	*<p>This event handler is triggered after parser ends processing a string that encodes a </code>java.util.Date</code> value.</p>
	 * @param context Parsing context information.
	 * @param name <code>true</code> or <code>false</code>
	 * @param value A non null instance of </code>java.util.Date</code>.
	 * @throws ParseException .-
	 */
	public void onDate(JSONParserContext context, String name, Date value) throws ParseException;
}
