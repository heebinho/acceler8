package models.dao;


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
}
