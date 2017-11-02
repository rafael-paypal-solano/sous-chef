package com.souschef.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TransactionRequiredException;

import org.apache.commons.beanutils.BeanUtils;

//http://blog.xebia.com/jpa-implementation-patterns-wrap-up/
public class BasicDAO<I extends Serializable, E extends EntityBean<I>> extends DAO<I,E>{
	private EntityManagerFactory entityManagerFactory;
	
	@SuppressWarnings("unchecked")	
	public BasicDAO(EntityManagerFactory entityManagerFactory) {
		Type type = (Type) getClass().getGenericSuperclass();
		ParameterizedType parametrizedType = (ParameterizedType)type;
		this.idClass = (Class<I>) parametrizedType.getActualTypeArguments()[0];
		this.entityClass = (Class<E>) parametrizedType.getActualTypeArguments()[1];
		this.entityManagerFactory = entityManagerFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void persist(EntityManager entityManager, E entity) throws DAOException {
		E existing = null;
		I id = entity.getId();
		
		try{
			existing = entityManager.find(entityClass, id);
			if(existing == null)
				entityManager.persist(entity);
			else{
				BeanUtils.copyProperties(existing, entity);
				entityManager.merge(existing);
			}
			
		}catch(EntityExistsException e){
			throw new DAOException(e);
		}catch(IllegalArgumentException e){
			throw new DAOException(e);
		}catch(TransactionRequiredException e){
			throw new DAOException(e);
		} catch (IllegalAccessException e) {
			throw new DAOException(e);
		} catch (InvocationTargetException e) {
			throw new DAOException(e);
		}
	}

	
	/**
	 * {@inheritDoc}
	 */	
	@Override
	public void remove(EntityManager entityManager, E entity) throws DAOException {
		E existing;
		
		try{
			existing = entityManager.find(entityClass, entity.getId());
			
			if(existing != null)
				entityManager.remove(existing);
		}catch(IllegalArgumentException e) {
			throw new DAOException(e);
		}catch(TransactionRequiredException e) {
			throw new DAOException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */	
	@Override
	public E findById(EntityManager entityManager, I key) throws DAOException {
		try{
			return entityManager.find(entityClass, key);
		}catch(IllegalArgumentException e){
			throw new DAOException(e);
		}
	}
	

	

	


	@Override
	public void persist(E entity) throws DAOException {
		EntityManager entityManager = null;
		
		try{
			entityManager = entityManagerFactory.createEntityManager();
			persist(entityManager, entity);
		}finally {
			if(entityManager != null)
				try {
					entityManager.close();
				}catch(Throwable t){
					
				}
		}
	}

	@Override
	public void remove(E entity) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public E findById(I key) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
