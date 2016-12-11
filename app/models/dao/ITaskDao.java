package models.dao;


import java.util.List;

import models.Task;
import models.Team;

/**
 * Task dao
 * 
 * @author Team RMG
 * 
 */
public interface ITaskDao extends IDao<Task, Integer> {

	/**
	 * Find all tasks of a team
	 * 
	 * @param team
	 * @return list of tasks
	 */
	List<Task> findAllTasksByTeam(Team team);
	
}
