package models.vm;

import play.data.validation.Constraints;

/**
 * Password model.
 * 
 * @author TEAM RMG
 *
 */
public class Password {
    
	@Constraints.Required
    private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String inputPassword) {
		this.password = inputPassword;
	}
}
