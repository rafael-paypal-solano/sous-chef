package com.souschef.json.parser.rpc;

import java.io.Serializable;

/**
 *<p>An structured element holds either simple elements (strings, numbers, dates, booleans) or other structured elements nested within it.</p>
  *<p>Copyright &copy; 1998 - 2010 <a href="http://www.castlebreck.com">Castlebreck Inc.</a>  All rights reserved.  This software may only be copied, altered, transferred or used in accordance<br/>
 *with the Castlebreck General Services Agreement which is available upon request from Castlebreck Inc. Visit www.castlebreck.com for<br/>
 *contact information.  This notice may not be removed and must be included with any copies of this work.</p>
 *@author rsolano
 *
 */
public interface JSONStructuredElement extends Serializable{
	
	/**
	 *<p>Stores <code>value</code>  inside this structured element.</p>
	 * @param name	A non empty character string.
	 * @param value	.-
	 */
	public void add(String name, Serializable value);
	
}
