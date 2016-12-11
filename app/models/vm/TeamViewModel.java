package models.vm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import models.Team;
import models.User;

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
	
	private User user;
	
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
	
	public List<UserViewModel> getMembers(){
		return members;
	}
	
	public List<UserViewModel> getMembersOrderedByTotalPoints() {
    	Stream<UserViewModel> orderedMembers = members.stream()
    			.sorted((m1,m2) -> Integer.compare(m2.getTotalPoints(), m1.getTotalPoints()) );
		return orderedMembers.collect(Collectors.toList());
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
