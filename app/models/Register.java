package models;

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
    private String inputPassword;

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
	public String getInputPassword() {
		return inputPassword;
	}

	/**
	 * @param inputPassword the inputPassword to set
	 */
	public void setInputPassword(String inputPassword) {
		this.inputPassword = inputPassword;
	}

	/**
     * Validate the authentication.
     *
     * @return null if validation ok, string with details otherwise
     */
    public String validate() {
        if (isBlank(email)) {
            return "Email is required";
        }

        if (isBlank(inputPassword)) {
            return "Password is required";
        }

        return null;
    }

    private boolean isBlank(String input) {
        return input == null || input.isEmpty() || input.trim().isEmpty();
    }

}
