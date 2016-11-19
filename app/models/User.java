package models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import play.data.format.Formats.NonEmpty;
import play.data.validation.Constraints.*;
import services.crypt.HashHelper;

/**
 * 
 * 
 * @author TEAM RMG
 * 
 */
@Entity
public class User extends Model {
	
	@Required
    @NonEmpty
    @Column(unique = true)
	private String email;
	
	private String token;
	
	@Required
    @NonEmpty
	private String password;
	
	@NonEmpty
	private boolean validated;
	
	private String strava_code;
	private String strava_token;
	private String strava_token_public;
	

	@ManyToMany(mappedBy = "users") //users --> collection on owner side (Team)
	private Set<Team> teams = new HashSet<Team>();
	
    
    public Set<Team> getTeams() {
        return teams;
    }

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}
	


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		//encrypt password (BCrypt)
		this.password = HashHelper.createPassword(password); 
	}
	
	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public String getStrava_code() {
		return strava_code;
	}

	public void setStrava_code(String strava_code) {
		this.strava_code = strava_code;
	}

	public String getStrava_token() {
		return strava_token;
	}

	public void setStrava_token(String strava_token) {
		this.strava_token = strava_token;
	}

	public String getStrava_token_public() {
		return strava_token_public;
	}

	public void setStrava_token_public(String strava_token_public) {
		this.strava_token_public = strava_token_public;
	}

	@Override
	public String toString() {
		return getEmail();
	}
	

}
