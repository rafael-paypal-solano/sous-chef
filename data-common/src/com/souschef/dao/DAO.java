package com.souschef.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.LockTimeoutException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PessimisticLockException;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;

import org.apache.openjpa.persistence.ArgumentException;

/**
 * <p>This is a blueprint for all classes that might be deemed as DAO objects.</p>
 * @author rsolano
 *
 * @param <I> A class or interface derived from <code>java.io.Serializable</code> that acts as entity's primary key.
 * @param <E> The entity class handled by this DAO.
 */
public abstract class DAO<I extends Serializable, E extends EntityBean<I>>  {
	/**
	 * The entity class handled by this DAO.
	 */
	protected Class<E> entityClass;
	
	/**
	 * A class or interface derived from <code>java.io.Serializable</code> that acts as entity's primary key.
	 */
	protected Class<I> idClass;
	
	/**
	 * <p>Persist an entity.</p>
	 * @param entityManager An entity manager that must be instantiated by the caller. Transaction handling is also caller's responsability.
	 * @param entity A non-null instance of the entity class.
	 * @throws DAOException If JPA frameworks could not persist the object.
	 */
	public abstract void persist(EntityManager entityManager, E entity) throws DAOException;
	
	/**
	 *  <p>Removes an entity.</p>
	 * @param entityManager  An entity manager that must be instantiated by the caller. Transaction handling is also caller's responsability.
	 * @param entity A non-null instance of the entity class.
	 * @throws DAOException
	 */
	public abstract void remove(EntityManager entityManager, E entity) throws DAOException;
	
	/**
	 * <p>Looks for an entity whose <code>id</code> property matches <code>key</code> parameter.</p>
	 * @param entityManager  An entity manager that must be instantiated by the caller. Transaction handling is also caller's responsability.
	 * @param key A non-null instance of the entity's primary key class
	 * @return The entity whose <code>id</code> property matches <code>key</code> parameter, or <code>null</code> if none is found. 
	 * @throws DAOException
	 */
	public abstract E findById(EntityManager entityManager, I key) throws DAOException;
		
	/**
	 * <p>Persist an entity.</p>
	 * <p>Derived classes may have an internal entity manager available for this method.</p>
	 * @param entity
	 * @throws DAOException
	 */
	public abstract void persist(E entity) throws DAOException;
	
	/**
	 * <p>Removes an entity.</p>
	 * <p>Derived classes may have an internal entity manager available for this method.</p>
	 * @param entity
	 * @throws DAOException
	 */
	public abstract void remove(E entity) throws DAOException;
	
	/**
	 *  <p>Looks for an entity whose <code>id</code> property matches <code>key</code> parameter.</p>
	 *  <p>Derived classes may have an internal entity manager available for this method.</p>
	 * @param key A non-null instance of the entity's primary key class
	 * @return The entity whose <code>id</code> property matches <code>key</code> parameter, or <code>null</code> if none is found. 
	 * @throws DAOException
	 */
	public abstract E findById(I key) throws DAOException;
	
	/**
	 * <p>Retrieves all records matching a filtering criteria defined in a JPA query.</p>
	 * @param entityManager An entity manager that must be instantiated by the caller.
	 * @param queryName A JPA query defined via annotations or XML.
	 * @param parameters A map containing values for query's parameters. Parameter names are map's keys. If <code>parameters==null</code> then the JPA query is assumed to have no parameters.
	 * @return All records matching query's filter.
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<E> all(EntityManager entityManager, String queryName, Map<String, Object> parameters) throws DAOException{
		try{ 
			Query query = entityManager.createNamedQuery(queryName);
			List<E> list;
			
			if(parameters != null ){
				for(Entry<String,Object> entry: parameters.entrySet()){
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
			list = query.getResultList();
			return list;
		}catch(IllegalStateException  e) {
			throw new DAOException(e);
		}
		catch(QueryTimeoutException e){
			throw new DAOException(e);
		}
		catch(TransactionRequiredException e) {
			throw new DAOException(e);
		}
		catch(PessimisticLockException e){
			throw new DAOException(e);
		}
		catch(LockTimeoutException e){
			throw new DAOException(e);
		}
		catch(ArgumentException e){
			throw new DAOException(e);
		}
	}	
	
	/**
	 * <p>Shorthand for <b><code>all(entityManager, queryName, null)</code></b>.</p>
	 * @param entityManager  An entity manager that must be instantiated by the caller.
	 * @param queryName A JPA query defined via annotations or XML.
	 * @return All records matching query's filter.
	 * @throws DAOException
	 */
	public List<E> all(EntityManager entityManager, String queryName) throws DAOException{
		return all(entityManager, queryName, null);
	}
	
	/**
	 *<p>Retrieves the first record matching a filtering criteria defined in a JPA query.</p>
	 * @param entityManager
	 * @param queryName
	 * @param parameters
	 * @return
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public E exists(EntityManager entityManager, String queryName, Map<String, Object> parameters) throws DAOException{
		try{ 
			Query query = entityManager.createNamedQuery(queryName);
						
			if(parameters != null ){
				for(Entry<String,Object> entry: parameters.entrySet()){
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
			return (E) query.getSingleResult();
		}
		catch(NoResultException  e) {
			return null;
		}catch(NonUniqueResultException  e){
			throw new DAOException(e);
		}
		catch(IllegalStateException  e) {
			throw new DAOException(e);
		}
		catch(QueryTimeoutException e){
			throw new DAOException(e);
		}
		catch(TransactionRequiredException e) {
			throw new DAOException(e);
		}
		catch(PessimisticLockException e){
			throw new DAOException(e);
		}
		catch(LockTimeoutException e){
			throw new DAOException(e);
		}
		catch(ArgumentException e){
			throw new DAOException(e);
		}
	}	
	
}
