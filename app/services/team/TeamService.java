package services.team;

import java.util.List;

import javax.persistence.EntityManager;

import models.Team;
import models.User;
import models.dao.ITeamDao;
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
		ITeamDao dao = new TeamDao(getEntityManager());
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
		ITeamDao dao = new TeamDao(getEntityManager());
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
		ITeamDao dao = new TeamDao(getEntityManager());
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
		ITeamDao dao = new TeamDao(getEntityManager());
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
		ITeamDao dao = new TeamDao(getEntityManager());
		return dao.getCoachedTeamsByUser(user);
	}

	/**
	 * All public teams
	 * 
	 * @return list of teams
	 */
	@Override
	public List<Team> findPublicTeams() {
		ITeamDao dao = new TeamDao(getEntityManager());
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
		ITeamDao dao = new TeamDao(getEntityManager());
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
		ITeamDao dao = new TeamDao(getEntityManager());
		dao.delete(team);
	}

	/**
	 * All teams
	 * 
	 * @return list of teams
	 */
	@Override
	public List<Team> findAllTeams() {
		ITeamDao dao = new TeamDao(getEntityManager());
		return dao.findAll();
	}


	
}
