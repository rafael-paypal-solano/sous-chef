package com.souschef.util;

import java.util.HashMap;

public class ParameterMap extends HashMap<String,Object>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9197563801943790046L;

	public ParameterMap append(String key, Object value) {
		put(key, value);
		return this;
	}
}
