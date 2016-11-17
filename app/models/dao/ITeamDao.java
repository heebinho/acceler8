package models.dao;


import java.util.List;

import models.Team;

/**
 * 
 * 
 * @author Team RMG
 */
public interface ITeamDao extends IDao<Team, Integer> {

	
	
	/**
	 * 
	 * @param name team name
	 * @return true if name is available
	 */
	boolean checkAvailable(String name);
	
	
	/**
	 * 
	 * @param teamId
	 * @return
	 */
	List<Team> getTeamsByUser(Integer userId);
	
}
