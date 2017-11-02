package com.souschef.domain.client;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DAOWrapperClient {
	private EntityManagerFactory entityManagerFactory;
	
	public DAOWrapperClient() {
		getEntityManagerFactory();
	}
	protected EntityManagerFactory getEntityManagerFactory() {
		if(entityManagerFactory == null)
			entityManagerFactory = Persistence.createEntityManagerFactory("domain-model-test");
		return entityManagerFactory;
	}	
}
