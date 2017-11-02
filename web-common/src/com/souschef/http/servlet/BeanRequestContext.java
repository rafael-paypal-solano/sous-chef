package com.souschef.http.servlet;

import java.io.Serializable;

public class BeanRequestContext<B extends Serializable> extends RequestContext {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2033463956978394189L;

	private B bean;

	public B getBean() {
		return bean;
	}

	public void setBean(B bean) {
		this.bean = bean;
	}
	
	
}
