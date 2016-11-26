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

	E save(E entity);
	void delete(E entity);
	E findById(K id);
	List<E> findAll();
	void flush();
}
