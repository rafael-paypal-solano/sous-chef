package com.souschef.json.parser;

/**
 *<p>This class provides context information to <code>com.castlebreck.json.parse.ParserEventListener</code> implementations.</p>
  *<p>Copyright &copy; 1998 - 2010 <a href="http://www.castlebreck.com">Castlebreck Inc.</a>  All rights reserved.  This software may only be copied, altered, transferred or used in accordance<br/>
 *with the Castlebreck General Services Agreement which is available upon request from Castlebreck Inc. Visit www.castlebreck.com for<br/>
 *contact information.  This notice may not be removed and must be included with any copies of this work.</p>
 *@author rsolano
 */
public class ParserContext {
	
	/**
 *<p>Set to <code>true</code> when there're not more characters to process.</p>
	 */
	private boolean eof=false;

	/**
 *<p>Last character read from source.</p>
	 */
	private int character = -1;
	
	
	public ParserContext(){		
	}

	/**
 *<p>Retrieves <code>eof</code> field value</p>
	 * @return .-
	 */
	boolean isEof() {
		return eof;
	}

	/**
 *<p>Sets <code>eof</code> field value</p>
	 * @return .-
	 */	
	void setEof(boolean eof) {
		this.eof = eof;
	}

	/**
 *<p>Retrieves <code>character</code> field value</p>
	 * @return .-
	 */		
	int getCharacter() {
		return character;
	}

	/**
 *<p>Sets <code>character</code> field value</p>
	 * @return .-
	 */	
	void setCharacter(int character) {
		this.character = character;
	}

}
