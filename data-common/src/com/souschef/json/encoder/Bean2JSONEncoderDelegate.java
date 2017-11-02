package com.souschef.json.encoder;

import java.io.PrintWriter;

/**
 *<p>This interface define the contract of classes tha implement custom bean encoding into JSON format.</p>
  *<p>Copyright &copy; 1998 - 2010 <a href="http://www.castlebreck.com">Castlebreck Inc.</a>  All rights reserved.  This software may only be copied, altered, transferred or used in accordance<br/>
 *with the Castlebreck General Services Agreement which is available upon request from Castlebreck Inc. Visit www.castlebreck.com for<br/>
 *contact information.  This notice may not be removed and must be included with any copies of this work.</p>
 *@author rsolano
 */
public interface Bean2JSONEncoderDelegate {
	
	/**
	 *<p><code>BeanHieararchy2JSONEncoder.encode(Object bean, Writer writer)</code> delegates encoding process <code>Bean2JSONEncoderDelegate.encode(Object bean, Writer writer)</code> method for each object belonging to the class this instance is bound to.</p>
	 * @param bean An object that must be either null or an instance of same class returned by <code>this.getManagedClass()</code>
	 * @param writer .-
	 * @throws JSONEncodingException
	 */
	public void encode(Object bean, PrintWriter writer) throws JSONEncodingException;
	
	/**
	 * 
	 * @return The class whose instances can be translated into JSON. 
	 */
	public Class<?> getManagedClass();
}
