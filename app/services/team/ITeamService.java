package services.team;

import models.Team;
import models.User;

/**
 * 
 * 
 * @author TEAM RMG
 *
 */
public interface ITeamService {
	
	
	boolean isAlreadyMember(User user, int teamId);
	
	boolean addNewMember(User user, int teamId);
	
	boolean removeMember(User user, int teamId);
	
	Team findById(int id);
	

}
