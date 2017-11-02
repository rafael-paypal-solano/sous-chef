package com.souschef.json.parser.rpc;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;

/**
 *<p>Instances of this class represent a JavaScript hash.</p>
 *<p>Copyright &copy; 1998 - 2010 <a href="http://www.castlebreck.com">Castlebreck Inc.</a>  All rights reserved.  This software may only be copied, altered, transferred or used in accordance<br/>
 *with the Castlebreck General Services Agreement which is available upon request from Castlebreck Inc. Visit www.castlebreck.com for<br/>
 *contact information.  This notice may not be removed and must be included with any copies of this work.</p>
 *@author rsolano
 *
 */
public class MapElement extends HashMap<String,Serializable> implements JSONStructuredElement, Map<String,Serializable>{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1708513462483713853L;

	/**
	 * {@inheritDoc}
	 */
	public void add(String name, Serializable value) {
		if(value != null)
			this.put(name, value);
	}	
}
