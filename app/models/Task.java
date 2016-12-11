package models;


import javax.persistence.*;

import play.data.validation.Constraints.Required;

/**
 * Task model
 * 
 * @author TEAM RMG
 * 
 */
@Entity
public class Task extends Model {
	
	@Required
	private String name;
	
	@Required
	private int meters = 0;
    
	@ManyToOne
	@JoinColumn(name="team")
	private Team team;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMeters() {
		return meters;
	}

	public void setMeters(int meters) {
		this.meters = meters;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	
}