package services.team;

import java.util.List;

import models.Team;
import models.User;

/**
 * Team service.
 * 
 * @author TEAM RMG
 *
 */
public interface ITeamService {
	
	boolean isAlreadyMember(User user, int teamId);
	
	boolean addNewMember(User user, int teamId);
	
	boolean removeMember(User user, int teamId);
	
	Team findById(int id);
	
	List<Team> getTeamsByUser(Integer userId);

	List<Team> findPublicTeams();
	
}
