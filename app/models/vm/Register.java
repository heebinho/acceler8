package models.vm;

import play.data.validation.Constraints;

/**
 * Register Model
 * 
 * @author TEAM RMG
 *
 */
public class Register {
	
    @Constraints.Required
    private String email;
    
	@Constraints.Required
    private String password;

    /**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
    
    /**
	 * @return the inputPassword
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param inputPassword the inputPassword to set
	 */
	public void setPassword(String inputPassword) {
		this.password = inputPassword;
	}

}
