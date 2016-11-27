package models.vm;

import play.data.validation.Constraints;

/**
 * Invite view model.
 * 
 * @author TEAM RMG
 *
 */
public class Invite {

    @Constraints.Required
    private String email;
    
    private int teamId;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
    
	
}
