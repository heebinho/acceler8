package models.dao;


import java.util.List;

import models.Team;

/**
 * Team dao.
 * 
 * @author Team RMG
 */
public interface ITeamDao extends IDao<Team, Integer> {

	
	
	/**
	 * Check if name is available
	 * 
	 * @param name Team name
	 * @return true if name is available
	 */
	boolean checkAvailable(String name);
	
	
	/**
	 * All the teams of the user
	 * 
	 * @param userId The id of the user
	 * @return List of teams
	 */
	List<Team> getTeamsByUser(Integer userId);
	
}
