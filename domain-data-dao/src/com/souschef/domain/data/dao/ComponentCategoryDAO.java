package com.souschef.domain.data.dao;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.souschef.dao.*;
import com.souschef.domain.data.model.ComponentCategory;

public class ComponentCategoryDAO extends BasicDAO<String, ComponentCategory> {	
	
	public ComponentCategoryDAO(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}

	
	public List<ComponentCategory> allComponentCategories(EntityManager entityManager) throws DAOException {
		return (List<ComponentCategory>)all(entityManager, "ComponentCategory.all");
	}
}
