package com.souschef.json.parser.bean;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import com.souschef.json.parser.JSONParser;
import com.souschef.json.parser.JSONParserContext;
import com.souschef.json.parser.JSONParserException;

/**
 *<p>This <code>com.castlebreck.json.parser.JSONParser</code> descendant decodes whole bean hierarchies. The caller only needs to pass 
 *a JSON string/stream containing an encoded JavaScript object (not an array) to any of the public <code>decode</code> methods.</p>
 *<p>A bean hierarchy is a tree built up with beans that references other beans through standard JavaBeans get/set property methods; be aware that stack overflow may occur if the hiearchy contains cycles.</p>
 *<p>Copyright &copy; 1998 - 2010 <a href="http://www.castlebreck.com">Castlebreck Inc.</a>  All rights reserved.  This software may only be copied, altered, transferred or used in accordance<br/>
 *with the Castlebreck General Services Agreement which is available upon request from Castlebreck Inc. Visit www.castlebreck.com for<br/>
 *contact information.  This notice may not be removed and must be included with any copies of this work.</p>
 * @author rsolano
 *
 */
public class JSON2BeanParser extends JSONParser{

    /**
     *<p>Customized event handlers; customized event handlers provide hints for handling odd classes (those whose properties do not follow the JavaBeans standard).</p>
     */
    Map<String,JSON2BeanParserEvent> parserEvents = new HashMap<String,JSON2BeanParserEvent>();
    
	/**
	 * <p>Equivalent to <code>super(eventListener)</code>.</p>
	 * @param eventListener
	 * @throws JSONParserException
	 */
	public JSON2BeanParser(JSON2BeanParserEventListener eventListener) throws JSONParserException {
		super(eventListener);
	}

	/**
	 * <p>Equivalent to <code>super(new JSON2BeanHierarchyParserEventListener())</code>.</p>
	 * @throws JSONParserException
	 * 
	 */
	public JSON2BeanParser( ) throws JSONParserException {
		this(new JSON2BeanParserEventListener());
	}
	
    /**
     * Register an parser event into this context.
     * @param event
     */
    public void registerEvent(JSON2BeanParserEvent event){
    	this.parserEvents.put(event.getManagedClass().getName(), event);
    }

	/**
	 * {@inheritDoc}
	 */
	public JSONParserContext parse(JSONParserContext context, Reader content) throws JSONParserException {
		if(context instanceof JSON2BeanParserContext);
			((JSON2BeanParserContext)context).parserEvents=parserEvents;
		return super.parse(context, content);
	}

	/**
	 * {@inheritDoc}
	 */
	public JSONParserContext parse(JSONParserContext context, String content) throws JSONParserException {
		if(context instanceof JSON2BeanParserContext);
			((JSON2BeanParserContext)context).parserEvents=parserEvents;		
		return super.parse(context, content);
	}
    
    
}
