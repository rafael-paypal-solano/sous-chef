package com.souschef.dao;

import java.io.Serializable;

/**
 * <p>This generic class defines the common features present in all entity beans</p>
 * @author rsolano
 *
 * @param <I> The Id (aka primary) key for this class.
 */
public abstract class EntityBean<I extends Serializable> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5140146102848361867L;
	
	/**
	 * 
	 * @return A <code>java.io.Serializable</code> instance that represents this entity's primary key
	 */
	public abstract I getId();
	
	/**
	 * 
	 * @param id A <code>java.io.Serializable</code> instance that represents this entity's primary key
	 */
	public abstract void setId(I id);
	
	
}
