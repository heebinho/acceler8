package services.team;

import java.util.List;

import models.Task;
import models.Team;
import models.User;

/**
 * Team services.
 * 
 * @author TEAM RMG
 *
 */
public interface ITeamService {
	
	/**
	 * Check if a user is already a member of a team
	 * 
	 * @param user
	 * @param teamId
	 * @return true if is member
	 */
	boolean isAlreadyMember(User user, int teamId);
	
	/**
	 * Add a user to a team
	 * 
	 * @param user
	 * @param teamId
	 * @return Team
	 */
	Team addNewMember(User user, int teamId);
	
	/**
	 * Remove member
	 * 
	 * @param user
	 * @param teamId
	 * @return true if removed
	 */
	boolean removeMember(User user, int teamId);
	
	/**
	 * Find by identifier
	 * 
	 * @param id identifier
	 * @return Team
	 */
	Team findById(int id);
	
	/**
	 * All teams of a given user
	 * 
	 * @param userId
	 * @return List of teams
	 */
	List<Team> getTeamsByUser(Integer userId);
	
	/**
	 * All coached teams of a given user
	 * 
	 * @param user
	 * @return List of teams
	 */
	List<Team> getCoachedTeamsByUser(User user);

	/**
	 * All public teams
	 * 
	 * @return list of teams
	 */
	List<Team> findPublicTeams();
	
	/**
	 * All teams
	 * 
	 * @return list of teams
	 */
	List<Team> findAllTeams();
	
    /**
     * Persist team
     * 
     * @param team to persist
     * @return Team or null
     */
	Team persistTeam(Team team);
	
	/**
	 * Delete a team, remove referenced entities
	 * 
	 * @param team to delete
	 */
	void deleteTeam(Team team);
	
	/**
	 * Add a task to a team
	 * 
	 * @param task
	 * @param team
	 * @return Team
	 */
	Task addNewTask(Task task, Team team);
	
	/**
	 * Remove task
	 * 
	 * @param id Task id
	 */
	void removeTask(int id);

	/**
	 * Find all tasks
	 * 
	 * @param team
	 */
	List<Task> findAllTasks(Team team);

	/**
	 * Check if name is available
	 * 
	 * @param name Team name
	 * @return true if name is available
	 */
	boolean isAvailable(String name);
	
}
