package com.souschef.dao;

public class DAOException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7883820965898447309L;

	public DAOException(Throwable cause) {
		super(cause);
		
	}

	public DAOException(String string, Throwable cause) {
		super(string, cause);
		
	}

}
