package com.souschef.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import com.souschef.json.encoder.JSONEncoder;


/**
  *<p>This class provide handy methods for strings transformation</p>
 *<p>Copyright &copy; 1998 - 2010 <a href="http://www.castlebreck.com">Castlebreck Inc.</a>  All rights reserved.  This software may only be copied, altered, transferred or used in accordance<br/>
 *with the Castlebreck General Services Agreement which is available upon request from Castlebreck Inc. Visit www.castlebreck.com for<br/>
 *contact information.  This notice may not be removed and must be included with any copies of this work.</p>
 * @author colin 
 */
public class StringUtil {
	/**
	 *<p></p> 
	 */
	public static final String  EMPTY_STRING="";
	
	
	public static String trim(String string){
		
		if(string == null)
			return EMPTY_STRING;
		return string.trim();
	}
	
	
	/**
	 * <p>Compares two strings. This method checks whether either string is null when comparing them;
	 * therefore if both string are null they are considered as equivalent. When only one is null they 
	 * are considered as equivalent if the other is empty(""), otherwise this method returns  <code>string1.compareTo(string2)==0</code>.</p>
	 * @param string1 Character string.
	 * @param string2 Character string.
	 * @return
	 */
	public static boolean stringSafeEquals(String string1, String string2){
		boolean isEquals = false;
		if(string1 != string2){
			if(!(string1 == null || string2 == null)){
				if(string1.length() == string2.length()){
					isEquals=string1.compareTo(string2)==0;
				}
			}else if(string1 == null) {
				isEquals = string2.length()==0;
			}else {
				isEquals = string1.length()==0;
			}
		}
		return isEquals;
	}
	
	/**
	 *<p>Creates a new string containing test from a classpath resource file.</p>
	 * @param resource Resource file path.
	 * @return A non null string containing file's text.
	 * @throws IOException .-
	 */
	public static String loadResourceAsString(Object object, String resource) throws IOException{
		InputStream input = object.getClass().getResourceAsStream(resource);
		byte array[] = new byte[input.available()];
		String content;
		
		input.read(array);
		content = new String(array);
		input.close();
		return content;
	}

	/**
	 *<p>Creates a new string containing test from a classpath resource file.</p>
	 * @param resource Resource file path.
	 * @return A non null string containing file's text.
	 * @throws IOException .-
	 */
	public static String loadResourceAsJSONString(Object object, String resource) throws IOException{
		InputStream input = object.getClass().getResourceAsStream(resource);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		PrintWriter writer = new PrintWriter(output);
		
		byte array[] = new byte[input.available()];
		String content;
		
		input.read(array);
		content = new String(array);
		JSONEncoder.encode(content, writer);
		writer.flush();
		output.flush();
		content =output.toString();
		input.close();
		return content;
	}
	
	/**
	 *<p>Creates a new string containing test from a classpath resource file.</p>
	 * @param resource Resource file path.
	 * @return A non null string containing file's text.
	 * @throws IOException .-
	 */
	public static String loadFileAsString(File resource) throws IOException{
		InputStream input = new FileInputStream(resource);
		byte array[] = new byte[input.available()];
		String content;
		
		input.read(array);
		content = new String(array);
		input.close();
		return content;
	}
	
	/**
 *<p>Deletes all occurrences of <code>key</code> from <code>content</code>.</p>
	 * @param content Non null string buiklder.
	 * @param key Non null string.
	 * @return <code>content</code>.
	 */
	public static StringBuilder delete(StringBuilder content, String key){
		int start = 0;
		int pos;
		
		while((pos = content.indexOf(key, start)) > -1){
			content.delete(pos, pos+key.length());
		}
		
		return content;
	}
	
	/**
	 *<p>Replaces all occurrences of <code>key</code> in <code>content</code> with <code>value</code>.</p>
	 * @param content
	 * @param key
	 * @param value
	 * @return
	 */
	public static StringBuilder replace(StringBuilder content, String key,  String value){
		int start = 0;
		int pos;
				
		if(!(key  == null || value == null)){
			while((pos = content.indexOf(key, start)) > -1){
				content.delete(pos, pos+key.length());
				if(pos < content.length()){
					content.insert(pos, value);
					start = pos+value.length();
				}
				else{
					content.append(value);
					start = content.length()-1;
				}
			}
		}
		return content;
	}
	
	/**
	 * 
	 * @param content
	 * @param keys
	 * @param values
	 * @param keyStartDelim
	 * @param keyEndDelim
	 * @return
	 */
	public static StringBuilder replace(StringBuilder content, String[] keys,  Object[] values,String keyStartDelim, String keyEndDelim) {
		int keyLen;     
		String searchVal;
		boolean useDelims = false;

		keyLen = keys.length;
		
		for ( int i=0; i< keyLen; i++){
			
			if ( values[i] == null){
				if ( useDelims){
					searchVal = keyStartDelim + keys[i] + keyEndDelim;
					content = StringUtil.replace(content, searchVal, "");
				}else{
					content = StringUtil.replace(content, keys[i], "");
				}
			}else{
				if ( useDelims ){
					searchVal = keyStartDelim + keys[i] + keyEndDelim;
					content = StringUtil.replace(content, searchVal, values[i].toString());
				}else{
					content = StringUtil.replace(content, keys[i], values[i].toString());
				}
			}
		}
		
		return content;

	}
	
	
	/**
	 * 
	 * @param source
	 * @return <code>null</code> if either <code>source == null</code> or </code>source.trim().length() == 0</code>, otherwise <code>source.trim()</code>.
	 */
	public static String trim2Null(String source){
		if(source != null){
			source = source.trim();
			if(source.length() == 0)
				source = null;
		}
		return source;
	}
}

