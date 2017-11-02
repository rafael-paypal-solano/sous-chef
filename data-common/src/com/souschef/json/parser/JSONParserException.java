package com.souschef.json.parser;

import com.souschef.json.JSONException;
/**
 *<p>This class wraps any exception thrown inside JSONParser's methods.</p>
 *<p>Copyright &copy; 1998 - 2010 <a href="http://www.castlebreck.com">Castlebreck Inc.</a>  All rights reserved.  This software may only be copied, altered, transferred or used in accordance<br/>
*with the Castlebreck General Services Agreement which is available upon request from Castlebreck Inc. Visit www.castlebreck.com for<br/>
*contact information.  This notice may not be removed and must be included with any copies of this work.</p>
*@author rsolano
*
*/
public class JSONParserException extends JSONException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3342616403484377822L;
	/**
 *<p>Constructs a new <code>JSONParserException</code> with the specified cause and a detail message of (cause==null ? null : cause.toString()) (which typically contains the class and detail message of cause). This constructor is useful when we only need to wrap throwables. </p>
	 * @param throwable the cause (which is saved for later retrieval by the Throwable.getCause() method). 
	 */
	public JSONParserException(Throwable cause) {
		super(cause);
	}
}
