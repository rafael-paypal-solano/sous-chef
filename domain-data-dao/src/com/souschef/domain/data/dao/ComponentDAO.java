package com.souschef.domain.data.dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.souschef.dao.*;
import com.souschef.domain.data.model.Component;
import com.souschef.domain.data.model.ComponentCategory;
import com.souschef.util.ParameterMap;

public class ComponentDAO extends BasicDAO<String, Component>{
	
	public ComponentDAO(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}

	public Component findComponentByName(EntityManager entityManager, String name) {
		return exists(
			entityManager, 
			"Component.byName", 
			(new ParameterMap()).append("name", name)
		);
	}

	public List<Component> componentsByCategory(EntityManager entityManager, ComponentCategory category) throws DAOException{
		Map<String, Object> parameters = new HashMap<String,Object>();
		parameters.put("category", category);
		return all(entityManager, "Component.byCategory", parameters);
	}
	
}
