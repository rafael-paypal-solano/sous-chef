package com.souschef.json.parser;

/**
 *<p>This class provides context information to <code>com.castlebreck.json.parse.ParserEventListener</code> implementations.</p>
  *<p>Copyright &copy; 1998 - 2010 <a href="http://www.castlebreck.com">Castlebreck Inc.</a>  All rights reserved.  This software may only be copied, altered, transferred or used in accordance<br/>
 *with the Castlebreck General Services Agreement which is available upon request from Castlebreck Inc. Visit www.castlebreck.com for<br/>
 *contact information.  This notice may not be removed and must be included with any copies of this work.</p> 
 *@author rsolano
 */

public abstract class JSONParserContext {
		
	/**
	 *<p>Stores all content read from input stream.</p>
	 */
	StringBuilder content = new StringBuilder();
	
	/**
*<p>Set to <code>true</code> when there're not more characters to process.</p>
	 */
	private boolean eof=false;

	/**
	 *<p>Last character read from source.</p>
	 */
	private int character = -1;
	
	/**
	 * <p>Previous character read.</p>
	 */
	private int previousCharacter=-1;
	
	/**
	 * When <code>true</code> it means that not elements were put into the structure that's the current element in the parsing stack.
	 */
	boolean emptyStructure;
	
	/**
	 * <p>When not null, the parser this class constructor to create objects.</p> 
	 */
	private Class<?> objectClass;
	
	public JSONParserContext(){		
	}

	/**
	 * <p>Sets the top/root node of the JSON hierarchy to be parsed. One has to set this reference before invoking the JSON parser.</p>
	 * @param root
	 */
	public abstract void setRoot(Object root);
	
	/**
	 * 
	 * @return Returns the top/root node of the JSON hierarchy to be parsed.
	 */
	public abstract Object getRoot();
	
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
	 * @return The class that overrides the default one selected by the parser.
	 */
	public Class<?> getObjectClass() {
		return objectClass;
	}

	/**
	 * @param objectClass The class that overrides the default one selected by the parser.
	 */	
	public void setObjectClass(Class<?> objectClass) {
		this.objectClass = objectClass;
	}

	/**
	 *<p>Sets <code>character</code> field value</p>
	 * @return .-
	 */	
	void setCharacter(int character) {
		content.append((char)character);
		if(!Character.isSpaceChar(this.character))
			previousCharacter=this.character;
		this.character = character;
	}

	public int getPreviousCharacter() {
		return previousCharacter;
	}

	public boolean isEmptyStructure() {
		return emptyStructure;
	}

	public void setEmptyStructure(boolean emptyStructure) {
		this.emptyStructure = emptyStructure;
	}
	
	
}
