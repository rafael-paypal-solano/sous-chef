package com.souschef.json.parser.bean;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.souschef.json.parser.JSONParserContext;
import com.souschef.json.parser.JSONParserEventListener;

/**
 * <p>This <code>com.castlebreck.json.parser.JSONParserEventListener</code> processes  <code>com.castlebreck.json.parser.JSON2BeanHierarchyParser</code>'s events.
 * Each <code>onXXX</code> method sets the value of the top structure's property identified by <code>name</code> parameter.
 * </p>
 *<p>Copyright &copy; 1998 - 2010 <a href="http://www.castlebreck.com">Castlebreck Inc.</a>  All rights reserved.  This software may only be copied, altered, transferred or used in accordance<br/>
 *with the Castlebreck General Services Agreement which is available upon request from Castlebreck Inc. Visit www.castlebreck.com for<br/>
 *contact information.  This notice may not be removed and must be included with any copies of this work.</p>
 * @author rsolano
 *
 */
public class JSON2BeanParserEventListener implements JSONParserEventListener{
	
	
	/**
	 * {@inheritDoc}
	 */
	public void onBoolean(JSONParserContext context, String name, boolean value)
			throws ParseException {
		JSON2BeanParserContext context_ = (JSON2BeanParserContext)context;
		JSON2BeanParserEvent parserEventListenerDelegate = context_.getParserEventListenerDelegate();
		if(parserEventListenerDelegate==null)
			context_.setProperty(name, value);
		else
			parserEventListenerDelegate.onBoolean(context, name, value);
	}

	/**
	 * {@inheritDoc}
	 */	
	public void onDate(JSONParserContext context, String name, Date value)
			throws ParseException {
		JSON2BeanParserContext context_ = (JSON2BeanParserContext)context;
		JSON2BeanParserEvent parserEventListenerDelegate = context_.getParserEventListenerDelegate();
		if(parserEventListenerDelegate==null)
			context_.setProperty(name, value);
		else
			parserEventListenerDelegate.onDate(context, name, value);
	}

	/**
	 * {@inheritDoc}
	 */	
	public void onNull(JSONParserContext context, String name)
			throws ParseException {
		JSON2BeanParserContext context_ = (JSON2BeanParserContext)context;
		JSON2BeanParserEvent parserEventListenerDelegate = context_.getParserEventListenerDelegate();
		if(parserEventListenerDelegate==null)
			context_.setProperty(name, null);
		else
			parserEventListenerDelegate.onNull(context, name);
	}

	/**
	 * {@inheritDoc}
	 */	
	public void onNumber(JSONParserContext context, String name, Number value)
			throws ParseException {
		JSON2BeanParserContext context_ = (JSON2BeanParserContext)context;
		JSON2BeanParserEvent parserEventListenerDelegate = context_.getParserEventListenerDelegate();
		if(parserEventListenerDelegate==null)
			context_.setProperty(name, value);
		else
			parserEventListenerDelegate.onNumber(context, name, value);
		
	}

	/**
	 * {@inheritDoc}
	 */	
	public void onString(JSONParserContext context, String name, String value)
			throws ParseException {
		JSON2BeanParserContext context_ = (JSON2BeanParserContext)context;
		JSON2BeanParserEvent parserEventListenerDelegate = context_.getParserEventListenerDelegate();
		if(parserEventListenerDelegate==null)
			context_.setProperty(name, value);
		else
			parserEventListenerDelegate.onString(context, name, value);
		
	}

	/**
	 * {@inheritDoc}
	 */	
	@SuppressWarnings("rawtypes")
	public void onStructureEnd(JSONParserContext context, String name, StructureTypeEnum type) throws ParseException {
		JSON2BeanParserContext context_ = (JSON2BeanParserContext)context;
		JSON2BeanParserEvent parserEventListenerDelegate = context_.getParserEventListenerDelegate();
		boolean pop = true;
		
		/* DEBUG *
		System.out.printf("onStructureEnd(\"%s\")\n", name);
		if(name != null && name.equals("queryColumnFormatterMap")){
			System.out.println();
		}
		
		/* */
		
		if(context_.isCustomProcessing()){
			if(parserEventListenerDelegate.onStructureEnd(context_, name, type)){			
				context_.setParserEventListenerDelegate(parserEventListenerDelegate=null);	
				context_.setCustomProcessing(false);
			}else{
				pop = false;
			}
		}
		if(pop){
			context_.pop();
		}
		
		/* */
		if(context_.top instanceof Collection && context_.isEmptyStructure()){
			((Collection)context_.top).clear();
		}else if(context_.top instanceof Map && context_.isEmptyStructure()){
			((Map)context_.top).clear();
		}
		/* */
	}

	/**
	 * {@inheritDoc}
	 */	
	@SuppressWarnings("unchecked")
	public void onStructureStart(JSONParserContext context, String name, StructureTypeEnum type) throws ParseException {
		JSON2BeanParserContext context_ = (JSON2BeanParserContext)context;
		JSON2BeanParserEvent parserEventListenerDelegate = context_.getParserEventListenerDelegate();
		int stackSize;
		/* DEBUG *
		System.out.printf("onStructureStart(\"%s\")\n", name);
		if(name != null && name.equals("queryColumnFormatterMap")){
			System.out.println();
		}		
		/* */
		if(context_.isCustomProcessing()){
			context_.setCustomProcessing(parserEventListenerDelegate.onStructureStart(context_, name, type));
		}else{
			stackSize = context_.stack.size();
			if(stackSize > 1 && context_.stack.get(stackSize-2) instanceof Map){
				Map<Object,Object> map = (Map<Object,Object>)context_.stack.get(stackSize-2);
				Object object = map.remove(null);
				if(object != null){
					map.put(name, object);
				}else{
					context_.push(name);
				}
			}else{
				context_.push(name);
				context_.setParserEventListenerDelegate(parserEventListenerDelegate = context_.parserEvents.get(context_.top.getClass().getName()));
				context_.setCustomProcessing(parserEventListenerDelegate != null);		
			}
		}	
		
		
	}

}
