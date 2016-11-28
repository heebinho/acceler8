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

	@Override
	public boolean isAlreadyMember(User user, int teamId) {
		
		return user.getTeams().stream()
				.anyMatch(t -> t.getId() == teamId);
	}
	
	@Override
	public boolean addNewMember(User user, int teamId) {
		ITeamDao dao = new TeamDao(getEntityManager());
		Team team = dao.findById(teamId);
		team.getUsers().add(user);
		dao.save(team);
		return true;
	}

	@Override
	public Team findById(int id) {
		ITeamDao dao = new TeamDao(getEntityManager());
		return dao.findById(id);
	}

	@Override
	public boolean removeMember(User user, int teamId) {
		ITeamDao dao = new TeamDao(getEntityManager());
		Team team = dao.findById(teamId);
		return team.getUsers().remove(user);
	}

	@Override
	public List<Team> getTeamsByUser(Integer userId) {
		ITeamDao dao = new TeamDao(getEntityManager());
		return dao.getTeamsByUser(userId);
	}
	




}
