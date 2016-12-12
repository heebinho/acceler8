package models;


import java.util.Date;
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
	private int meters;
    
	@ManyToOne
	@JoinColumn(name="team")
	private Team team;
	
    private Date ts;

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

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}
	
}