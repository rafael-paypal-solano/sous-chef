package com.souschef.json.parser.rpc;

import java.io.Serializable;

/**
 *<p>This class encloses handy static functions to format objects that implements <code>java.io.Serializable</code> into JSON strings.</p>
  *<p>Copyright &copy; 1998 - 2010 <a href="http://www.castlebreck.com">Castlebreck Inc.</a>  All rights reserved.  This software may only be copied, altered, transferred or used in accordance<br/>
 *with the Castlebreck General Services Agreement which is available upon request from Castlebreck Inc. Visit www.castlebreck.com for<br/>
 *contact information.  This notice may not be removed and must be included with any copies of this work.</p>
 *@author rsolano
 *
 */
public class PrintUtils {
	
	/**
 *<p>Dummy object that's used instead of java's <code>null</code>.</p>
 *<p>Copyright &copy; 1998 - 2010 <a href="http://www.castlebreck.com">Castlebreck Inc.</a>  All rights reserved.  This software may only be copied, altered, transferred or used in accordance<br/>with the Castlebreck General Services Agreement which is available upon request from Castlebreck Inc. Visit www.castlebreck.com for<br/>contact information.  This notice may not be removed and must be included with any copies of this work.</p>
	 *
	 */
	public static final class NullConstant implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -5507329872240134834L;
		
		/**
		 * Empty constructor.
		 */
		NullConstant(){}
	};
	
	/**
	 * Singleton instance of <code>NullConstant</code>.
	 */
	public static final NullConstant NULL = new NullConstant();
	
	
	
}
