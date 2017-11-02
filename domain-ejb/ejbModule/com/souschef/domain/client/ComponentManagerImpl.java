package com.souschef.domain.client;
//http://www.thoughts-on-java.org/use-jpa-next-project/
import java.util.List;


import javax.persistence.EntityManager;

import com.souschef.client.ClientException;
import com.souschef.dao.DAOException;
import com.souschef.domain.data.dao.ComponentCategoryDAO;
import com.souschef.domain.data.dao.ComponentDAO;
import com.souschef.domain.data.model.Component;
import com.souschef.domain.data.model.ComponentCategory;


public class ComponentManagerImpl extends DAOWrapperClient implements ComponentManager{
	
	protected ComponentCategoryDAO componentCategoryDAO;		
	protected ComponentDAO componentDAO;
	
	public ComponentManagerImpl() {
		super();
		try {
			componentCategoryDAO = new ComponentCategoryDAO(getEntityManagerFactory());
			componentDAO = new ComponentDAO(getEntityManagerFactory());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	
	@Override
	public List<ComponentCategory> allComponentCategories()  throws ClientException{
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try {
			return componentCategoryDAO.allComponentCategories(entityManager);
		}catch(DAOException e) {
			throw new ClientException(e);
		}
	}
	@Override
	public void saveComponent(Component component)  throws ClientException{
		try {
			componentDAO.persist(component);			
		}catch(DAOException e) {
			throw new ClientException(e);
		}catch(IllegalStateException e){
			throw new ClientException(e);
		}finally {
			
		}
		
	}
	@Override
	public Component findComponentById(String id)  throws ClientException{
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try{
			return componentDAO.findById(entityManager, id);
		}catch(DAOException e){
			throw new ClientException(e);
		}
	}
	@Override
	public void removeComponent(String id)  throws ClientException{
		Component component;
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try {
			component = componentDAO.findById(entityManager, id);
			if(component != null)
				componentDAO.remove(entityManager, component);
			
		}catch(DAOException e) {
			throw new ClientException(e);
		}catch(IllegalStateException e){
			throw new ClientException(e);
		}
	}
	
	
	
	@Override
	public void saveComponentCategory(ComponentCategory componentCategory) throws ClientException {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try {
			componentCategoryDAO.persist(entityManager, componentCategory);						
		}catch(DAOException e) {
			throw new ClientException(e);
		}catch(IllegalStateException e){
			throw new ClientException(e);
		}		
	}
	
	@Override
	public ComponentCategory findComponentCategoryById(String id) throws ClientException {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try{
			return componentCategoryDAO.findById(entityManager, id);
		}catch(DAOException e){
			throw new ClientException(e);
		}
	}
	
	@Override
	public void removeComponentCategory(String id) throws ClientException {
		ComponentCategory category;
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try {
			category = componentCategoryDAO.findById(entityManager, id);
			
			
			if(category != null)
				componentCategoryDAO.remove(entityManager, category);
		}catch(DAOException e) {
			throw new ClientException(e);
		}catch(IllegalStateException e){
			throw new ClientException(e);
		}			
	}

	@Override
	public List<Component> componentsByCategoryId(String id) throws ClientException {
		ComponentCategory category;
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try{
			category = componentCategoryDAO.findById(entityManager, id);
			if(category == null)
				return null;
			return componentDAO.componentsByCategory(entityManager, category);
		}catch(DAOException e){
			throw new ClientException(e);
		}		
	}



	@Override
	public Component findComponentByName(String name) throws ClientException {
		Component component;
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try{
			component = componentDAO.findComponentByName(entityManager, name);
			return component;
		}catch(DAOException e){
			throw new ClientException(e);
		}	
	}

	
	
}
