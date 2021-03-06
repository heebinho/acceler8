package services.team;

import java.util.List;

import javax.persistence.EntityManager;

import models.Task;
import models.Team;
import models.User;
import models.dao.ITaskDao;
import models.dao.ITeamDao;
import models.dao.TaskDao;
import models.dao.TeamDao;
import services.Service;


/**
 * Team services.
 * 
 * @author TEAM RMG
 *
 */
public class TeamService extends Service implements ITeamService {

	public TeamService(EntityManager em){
		super(em);
	}

	/**
	 * Check if a user is already a member of a team
	 * 
	 * @param user
	 * @param teamId
	 * @return true if is member
	 */
	@Override
	public boolean isAlreadyMember(User user, int teamId) {
		
		return user.getTeams().stream()
				.anyMatch(t -> t.getId() == teamId);
	}
	
	/**
	 * Add a user to a team
	 * 
	 * @param user
	 * @param teamId
	 * @return Team
	 */
	@Override
	public Team addNewMember(User user, int teamId) {
		ITeamDao dao = new TeamDao(em());
		Team team = dao.findById(teamId);
		team.getUsers().add(user);
		return dao.save(team);
	}

	/**
	 * Remove member
	 * 
	 * @param user
	 * @param teamId
	 * @return true if removed
	 */
	@Override
	public boolean removeMember(User user, int teamId) {
		ITeamDao dao = new TeamDao(em());
		Team team = dao.findById(teamId);
		return team.getUsers().remove(user);
	}
	
	/**
	 * Find by identifier
	 * 
	 * @param id identifier
	 * @return Team
	 */
	@Override
	public Team findById(int id) {
		ITeamDao dao = new TeamDao(em());
		return dao.findById(id);
	}

	/**
	 * All teams of a given user
	 * 
	 * @param userId
	 * @return List of teams
	 */
	@Override
	public List<Team> getTeamsByUser(Integer userId) {
		ITeamDao dao = new TeamDao(em());
		return dao.getTeamsByUser(userId);
	}
	
	/**
	 * All coached teams of a given user
	 * 
	 * @param user
	 * @return List of teams
	 */
	@Override
	public List<Team> getCoachedTeamsByUser(User user) {
		ITeamDao dao = new TeamDao(em());
		return dao.getCoachedTeamsByUser(user);
	}

	/**
	 * All public teams
	 * 
	 * @return list of teams
	 */
	@Override
	public List<Team> findPublicTeams() {
		ITeamDao dao = new TeamDao(em());
		return dao.getPublicTeams();
	}

    /**
     * Persist team
     * 
     * @param team to persist
     * @return Team or null
     */
	@Override
	public Team persistTeam(Team team) {
		ITeamDao dao = new TeamDao(em());
		return dao.save(team);
	}

	/**
	 * All teams of a given user
	 * 
	 * @param userId
	 */
	@Override
	public void deleteTeam(Team team) {
		team.getUsers().clear();
		ITeamDao dao = new TeamDao(em());
		dao.delete(team);
	}

	/**
	 * All teams
	 * 
	 * @return list of teams
	 */
	@Override
	public List<Team> findAllTeams() {
		ITeamDao dao = new TeamDao(em());
		return dao.findAll();
	}


	/**
	 * Add a task to a team
	 * 
	 * @param task
	 * @param teamId
	 * @return Team
	 */
	@Override
	public Task addNewTask(Task task, Team team) {

		task.setTeam(team);
		ITaskDao taskDao = new TaskDao(em());
		return taskDao.save(task);
	}

	/**
	 * Remove task
	 * 
	 * @param id Task id
	 */
	@Override
	public void removeTask(int id) {
		ITaskDao dao = new TaskDao(em());
		Task task = dao.findById(id);
		dao.delete(task);
	}

	/**
	 * Find all tasks
	 * 
	 * @param team
	 */
	@Override
	public List<Task> findAllTasks(Team team) {
		ITaskDao dao = new TaskDao(em());
		return dao.findAllTasksByTeam(team);
	}
	
	/**
	 * Check if name is available
	 * 
	 * @param name Team name
	 * @return true if name is available
	 */
	@Override
	public boolean isAvailable(String name) {
		
		ITeamDao dao = new TeamDao(em());
		return dao.isAvailable(name);
	}
	
}
