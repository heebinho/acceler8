package models.dao;

import javax.persistence.EntityManager;
import models.Task;

/**
 * Task dao.
 * 
 * @author TEAM RMG
 *
 */
public class TaskDao extends Dao<Task, Integer> implements ITaskDao {

	public TaskDao(EntityManager em) {
		super(Task.class, em);
	}


}
