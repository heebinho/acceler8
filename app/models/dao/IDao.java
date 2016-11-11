package models.dao;

import java.io.Serializable;
import java.util.List;

public interface IDao<E, K extends Serializable> {

	E save(E entity);
	void delete(E entity);
	E findById(K id);
	List<E> findAll();
	void flush();
}
