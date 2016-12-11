package models.dao;

import java.io.Serializable;
import java.util.List;


/**
 * Generic dao.
 * 
 * @author TEAM RMG
 *
 * @param <E> Entity
 * @param <K> Key
 */
public interface IDao<E, K extends Serializable> {

	/**
	 * Save a generic entity
	 * 
	 * @param entity to persist
	 * @return saved entity
	 */
	E save(E entity);
	
	/**
	 * Delete a given entity
	 * 
	 * @param entity to delete
	 */
	void delete(E entity);
	
	/**
	 * Find an entity by its identifier
	 * 
	 * @param id identifier
	 * @return entity or null
	 */
	E findById(K id);
	
	/**
	 * Find all entities of a given type
	 * @return list of entity
	 */
	List<E> findAll();
	
	/**
	 * Synchronize the persistence context to the underlying database.
	 */
	void flush();
}
