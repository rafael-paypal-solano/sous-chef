package com.souschef.json.parser.rpc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * p>Instances of this class represent a JavaScript arrays.</p>
  *<p>Copyright &copy; 1998 - 2010 <a href="http://www.castlebreck.com">Castlebreck Inc.</a>  All rights reserved.  This software may only be copied, altered, transferred or used in accordance<br/>
 *with the Castlebreck General Services Agreement which is available upon request from Castlebreck Inc. Visit www.castlebreck.com for<br/>
 *contact information.  This notice may not be removed and must be included with any copies of this work.</p>
 *@author rsolano
 *
 */
public class ListElement extends ArrayList<Serializable> implements JSONStructuredElement, List<Serializable>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8173200784896170724L;

	/**
	 * {@inheritDoc}
	 */
	public void add(String name, Serializable value) {
		if(value != null)
			super.add(value);
	}

}
