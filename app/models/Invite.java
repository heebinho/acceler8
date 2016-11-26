package models;

import play.data.validation.Constraints;

/**
 * Invite Model
 * 
 * @author TEAM RMG
 *
 */
public class Invite {

    @Constraints.Required
    private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
	
}
