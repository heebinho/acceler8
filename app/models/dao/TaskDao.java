package models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import models.Task;
import models.Team;

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

	/**
	 * Find all tasks of a team
	 * 
	 * @param team
	 * @return list of tasks
	 */
	@Override
	public List<Task> findAllTasksByTeam(Team team) {
		Query query = getEntityManager()
				.createQuery("select t from Task t"
				+ " where t.team = :team")
				.setParameter("team", team);
		
		@SuppressWarnings("unchecked")
		List<Task> tasks = query.getResultList();
		
		return tasks;
	}


}
