package models.vm;

/**
 * Settings view model.
 * 
 * @author TEAM RMG
 *
 */
public class Settings {
	
	private String email;
    
    private String password;
	
    private String passwordConfirmation;
	
    private String strava_token_public;
    
    private String authorizationUrl;
    
    private boolean authorized = false;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String inputPassword) {
		this.password = inputPassword;
	}
	
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public String getStrava_token_public() {
		return strava_token_public;
	}

	public void setStrava_token_public(String strava_public_token) {
		this.strava_token_public = strava_public_token;
	}

	public String getAuthorizationUrl() {
		return authorizationUrl;
	}

	public void setAuthorizationUrl(String authorizationUrl) {
		this.authorizationUrl = authorizationUrl;
	}

	public boolean isAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}
}
