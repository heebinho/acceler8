package models.vm;

import models.Team;
import models.User;

/**
 * A specialized view model for the team.show view.
 * 
 * 
 * @author TEAM RMG
 *
 */
public class TaskViewModel {
	
	private boolean isMember = false;
	
	private Team team;
	
	private User user;
	
	public TaskViewModel(){

	}
	
	public boolean isMember() {
		return isMember;
	}

	public void setMember(boolean isMember) {
		this.isMember = isMember;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	/**
	 * Get authenticated user
	 * @return user The authenticated user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Set authenticated user
	 * @param user The authenticated user
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
