package com.souschef.json.encoder;

import java.io.PrintWriter;

/**
 *<p>This class contains the functionality shared by all JSON encoders.</p>
 *<p>Copyright &copy; 1998 - 2010 <a href="http://www.castlebreck.com">Castlebreck Inc.</a>  All rights reserved.  This software may only be copied, altered, transferred or used in accordance<br/>
 *with the Castlebreck General Services Agreement which is available upon request from Castlebreck Inc. Visit www.castlebreck.com for<br/>
 *contact information.  This notice may not be removed and must be included with any copies of this work.</p>
 *@author rsolano
 */
public abstract class JSONEncoder {
	/**
	 *<p><code>&quot;null&quot;</code></p>
	 */
	public static String NULL="null";
		
	/**
	 *<p>Initializes fields that do not grab their initial values from external sources.</p>
	 */
	protected JSONEncoder(){		
	}
	
	/**
	 *<p>Creates a JSON string containing <code>bean</code>'s properties.</p>
	 *<p>The each <code>JSONEncoder</code> descendant defines its own translation policy.</p>
	 * @param bean A reference of any type. May be null 
	 * @param writer A non null reference to a <code>java.io.Writer</code> instance.
	 * @throws JSONEncodingException .-
	 */
	public abstract void encode(Object bean, PrintWriter writer) throws JSONEncodingException;
	
	/**
	 *<p>Creates a JSON string containing <code>bean</code>'s properties.</p>
	 *<p>The each <code>JSONEncoder</code> descendant defines its own translation policy.</p>
	 * @param bean A reference of any type. May be null 
	 * @param string A non null reference to a <code>java.lang.StringBuilder</code> instance.
	 * @throws JSONEncodingException .-
	 */
	public abstract void encode(Object bean, StringBuilder string) throws JSONEncodingException;
	
	/**
	 * <p>This method translates <code>string</code> into a JSON STRING</p>
	 * @param string Character string containing that will be translated in to JSON.
	 * @param writer Print writer object.
	 */
	public static void encode(String string, PrintWriter writer){
		 if (string == null) {
	            writer.write(NULL);
	            return;
	        }

	        char         c = 0;
	        int          i;
	        int          len = string.length();
	        String       t;

	        writer.append('"');
	        for (i = 0; i < len; i++) {
	            c = string.charAt(i);
	            switch (c) {
	            case '\\':
	            case '"':
	            case '/':
	            	writer.write('\\');
	            	writer.append(c);
	                break;
	            case '\b':
	            	writer.append("\\b");
	                break;
	            case '\t':
	            	writer.append("\\t");
	                break;
	            case '\n':
	            	writer.append("\\n");
	                break;
	            case '\f':
	            	writer.append("\\f");
	                break;
	            case '\r':
	            	writer.append("\\r");
	               break;
	            default:
	                if (c < ' ') {
	                    t = "000" + Integer.toHexString(c);
	                    writer.append("\\u" + t.substring(t.length() - 4));
	                } else {
	                	writer.append(c);
	                }
	            }
	        }
	        writer.append('"');		
	}
	
}
