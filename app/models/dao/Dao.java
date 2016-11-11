package models.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import models.Model;
import play.db.jpa.Transactional;

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

	public Class<E> getPersistentClass() {
		return persistentClass;
	}

	@Override
	public E save(E entity) {
		E mergedEntity = getEntityManager().merge(entity);
		return mergedEntity;
	}

	@Override
	public void delete(E entity) {
		//getEntityManager().remove(entity);
		if(Model.class.isAssignableFrom(persistentClass)){
			getEntityManager().remove(
					getEntityManager().getReference(entity.getClass(),
							((Model)entity).getId()));
		} else{
			entity = getEntityManager().merge(entity);
			getEntityManager().remove(entity);
		}
	}

	@Override
	@Transactional(readOnly=true)
	public E findById(K id) {
		E entity = (E)getEntityManager().find(getPersistentClass(), id);
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findAll() {
		return getEntityManager()
				.createQuery("select x from " + getPersistentClass().getSimpleName() + " x")
				.getResultList();
	}

	@Override
	public void flush() {
		getEntityManager().flush();
	}
	

}
