package models.dao;


import java.util.List;

import models.Team;
import models.User;

/**
 * Team dao
 * 
 * @author Team RMG
 * 
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
	
	/**
	 * All coached teams of the user
	 * 
	 * @param user The user
	 * @return List of teams
	 */
	List<Team> getCoachedTeamsByUser(User user);

	
	/**
	 * Get all public teams
	 * 
	 * @return List of teams
	 */
	List<Team> getPublicTeams();
	
	
	
}
