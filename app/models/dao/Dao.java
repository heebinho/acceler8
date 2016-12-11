package models.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import models.Model;
import play.db.jpa.Transactional;


/**
 * Generic dao implementation.
 *
 * 
 * @author TEAM RMG
 *
 * @param <E> Entity
 * @param <K> Key
 */
public abstract class Dao<E, K extends Serializable> implements IDao<E, K> {
	
	private Class<E> persistentClass;
	
	private EntityManager entityManager;
	
	public Dao(Class<E> persistentClass, EntityManager em ) {
		this.persistentClass = persistentClass;
		this.entityManager = em;
	}

	protected EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Get the underlying persistence class
	 * 
	 * @return Class<E>
	 */
	public Class<E> getPersistentClass() {
		return persistentClass;
	}

	/**
	 * Save a generic entity
	 * 
	 * @param entity to persist
	 * @return saved entity
	 */
	@Override
	public E save(E entity) {
		E mergedEntity = getEntityManager().merge(entity);
		return mergedEntity;
	}

	/**
	 * Delete a given entity
	 * 
	 * @param entity to delete
	 */
	@Override
	public void delete(E entity) {
		if(Model.class.isAssignableFrom(persistentClass)){
			getEntityManager().remove(
					getEntityManager().getReference(entity.getClass(),
							((Model)entity).getId()));
		} else{
			entity = getEntityManager().merge(entity);
			getEntityManager().remove(entity);
		}
	}

	/**
	 * Find an entity by its identifier
	 * 
	 * @param id identifier
	 * @return entity or null
	 */
	@Override
	@Transactional(readOnly=true)
	public E findById(K id) {
		E entity = (E)getEntityManager().find(getPersistentClass(), id);
		return entity;
	}

	/**
	 * Find all entities of a given type
	 * @return list of entity
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<E> findAll() {
		return getEntityManager()
				.createQuery("select x from " + getPersistentClass().getSimpleName() + " x")
				.getResultList();
	}

	/**
	 * Synchronize the persistence context to the underlying database.
	 */
	@Override
	public void flush() {
		getEntityManager().flush();
	}
	

}
