package models.vm;

import javastrava.api.v3.model.StravaAthlete;
import models.User;

/**
 * A specialized view model for a user.
 * 
 * 
 * @author TEAM RMG
 *
 */
public class UserViewModel {
	
	private User user;
	
	private StravaAthlete athlete;
	
	private String profileImage = "/assets/images/user.png";
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public StravaAthlete getAthlete() {
		return athlete;
	}
	public void setAthlete(StravaAthlete athlete) {
		this.athlete = athlete;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

}
