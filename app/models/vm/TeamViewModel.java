package models.vm;

import java.util.ArrayList;
import java.util.List;
import models.Team;

/**
 * A specialized view model for the team.show view.
 * 
 * 
 * @author TEAM RMG
 *
 */
public class TeamViewModel {
	
	private boolean isMember = false;
	
	private List<UserViewModel> members;
	
	private Team team;
	
	public TeamViewModel(){
		members = new ArrayList<>();
	}
	
	public boolean isMember() {
		return isMember;
	}

	public void setMember(boolean isMember) {
		this.isMember = isMember;
	}
	
	public boolean addMember(UserViewModel uVm){
		return members.add(uVm);
	}
	
	public List<UserViewModel> getMembers() {
		return members;
	}

	public void setMembers(List<UserViewModel> members) {
		this.members = members;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}
