package models.vm;

import play.data.validation.Constraints;

/**
 * Forgot password form.
 * 
 * @author TEAM RMG
 *
 */
public class EMail {
	
    @Constraints.Required
    private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public EMail(){}
	
	public EMail(String email){
		this.email = email;
	}

}
