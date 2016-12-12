package models.vm;

import java.util.ArrayList;
import java.util.List;

import javastrava.api.v3.model.StravaActivity;
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
	
	private int totalPoints = 0;
	
	private int weeklyPoints = 0;
	
	private int monthlyPoints = 0;
	
	private List<StravaActivity> activities = new ArrayList<StravaActivity>();
	
	private int completed = 0;
	
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
	public int getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}
	public int getWeeklyPoints() {
		return weeklyPoints;
	}
	public void setWeeklyPoints(int weeklyPoints) {
		this.weeklyPoints = weeklyPoints;
	}
	public int getMonthlyPoints() {
		return monthlyPoints;
	}
	public void setMonthlyPoints(int monthlyPoints) {
		this.monthlyPoints = monthlyPoints;
	}
	public List<StravaActivity> getActivities() {
		return activities;
	}
	public void setActivities(List<StravaActivity> activities) {
		this.activities = activities;
	}
	public int getCompleted() {
		return completed;
	}
	public void setCompleted(int completed) {
		this.completed = completed;
	}

}
