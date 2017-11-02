package com.souschef.json.parser.rpc;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import com.souschef.json.parser.JSONParserContext;
import com.souschef.json.parser.JSONParserEventListener;

/**
 * 
 *<p>Copyright &copy; 1998 - 2010 <a href="http://www.castlebreck.com">Castlebreck Inc.</a>  All rights reserved.  This software may only be copied, altered, transferred or used in accordance<br/>
 *with the Castlebreck General Services Agreement which is available upon request from Castlebreck Inc. Visit www.castlebreck.com for<br/>
 *contact information.  This notice may not be removed and must be included with any copies of this work.</p>
 *
 */
public class RPCParserEventListener implements JSONParserEventListener{

	/**
 *<p>Adds <code> to the top of JSONStructuredElement's stack</p>
	 * @param context Parser context.
	 * @param name Name for referencing <code>value</code>.
	 * @param value .-
	 */
	protected void addValue(JSONParserContext context, String name, Serializable value){
		RPCParserContext parserContext = (RPCParserContext) context;
		JSONStructuredElement top = parserContext.getTop();
		top.add(name, value);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void onBoolean(JSONParserContext context, String name, boolean value)
			throws ParseException {
		addValue(context, name, value);
	}

	/**
	 * {@inheritDoc}
	 */	
	public void onDate(JSONParserContext context, String name, Date value)
			throws ParseException {
		addValue(context, name, value);		
	}

	/**
	 * {@inheritDoc}
	 */	
	public void onNull(JSONParserContext context, String name)
			throws ParseException {
		addValue(context, name, null);
	}

	/**
	 * {@inheritDoc}
	 */	
	public void onNumber(JSONParserContext context, String name, Number value)
			throws ParseException {
		addValue(context, name, value);
	}

	/**
	 * {@inheritDoc}
	 */	
	public void onString(JSONParserContext context, String name, String value)
			throws ParseException {
		addValue(context, name, value);
	}

	/**
	 * {@inheritDoc}
	 */	
	public void onStructureEnd(JSONParserContext context, String name,
			StructureTypeEnum type) throws ParseException {
		RPCParserContext parserContext = (RPCParserContext)context;
		parserContext.pop();
	}

	/**
	 * {@inheritDoc}
	 */	
	public void onStructureStart(JSONParserContext context, String name,
			StructureTypeEnum type) throws ParseException {
		RPCParserContext parserContext = (RPCParserContext)context;
		JSONStructuredElement parent = parserContext.getTop();
		JSONStructuredElement element;
		
		switch(type)
		{
	        case ARRAY_TYPE: 
	            element = new ListElement();
	            break;

	        case MAP_TYPE:
	        default:
	            element = new MapElement();
	            break;
		}
		
		if(parent != null)
			parent.add(name, element);
		
		parserContext.push(element);
	}

}
