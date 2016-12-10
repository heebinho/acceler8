package models.vm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javastrava.api.v3.model.StravaActivity;
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
		//order by total points
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

}
