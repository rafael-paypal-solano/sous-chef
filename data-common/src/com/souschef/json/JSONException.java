package com.souschef.json;





public class JSONException extends Exception{
	
	
	private static final long serialVersionUID = 835762200390720832L;

	public JSONException(Throwable cause){		
		super(cause.getMessage(), cause);
	}
}
