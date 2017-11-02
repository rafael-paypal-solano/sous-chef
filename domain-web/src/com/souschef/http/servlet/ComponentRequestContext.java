package com.souschef.http.servlet;

import com.souschef.domain.data.model.Component;

public class ComponentRequestContext extends BeanRequestContext<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1049348282511174780L;

	public Component getBean() {
		return super.getBean();
	}

	public void setBean(Component bean) {
		// TODO Auto-generated method stub
		super.setBean(bean);
	}

}
