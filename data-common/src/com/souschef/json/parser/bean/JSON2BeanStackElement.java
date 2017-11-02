package com.souschef.json.parser.bean;

/**
 * <p>Each element in the parsing stack is wrapped within this class in order to provide post-creation processing.</p>
 */
public class JSON2BeanStackElement {

	/**
	 * <p>Object created by the json parser.</p>
	 */
	Object object;
	
	/**
	 * <p>Identifier assigned to this object in the JSON graph.</p>
	 */
	String name;

	/**
	 * @param object Object created by the json parser.
	 * @param name Identifier assigned to this object in the JSON graph.
	 */
	public JSON2BeanStackElement(Object object, String name) {
		super();
		this.object = object;
		this.name = name;
	}

	/**
	 *Object created by the json parser.
	 */
	public Object getObject() {
		return object;
	}

	/**
	 *@param Object created by the json parser.
	 */
	public void setObject(Object object) {
		this.object = object;
	}

	/**
	 * Identifier assigned to this object in the JSON graph
	 */
	public String getName() {
		return name;
	}

	/**
	 * @name Identifier assigned to this object in the JSON graph
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	
}
