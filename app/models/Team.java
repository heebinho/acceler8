package models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import play.data.validation.Constraints.*;

/**
 * Team Model
 * 
 * @author Team RMG
 *
 */
@Entity
public class Team extends Model {
	
	public Team(){}
	
	@Required
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "TeamMember",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<User>();
	
	@ManyToOne
	@JoinColumn(name="coach")
	private User coach;
	
	private String description;
	
	private String city;
	
	private String country;
	
	private String website;
	
	private boolean isPrivate = false;
	
	@OneToMany()
    private Set<Task> tasks = new HashSet<Task>();

    public Set<User> getUsers() {
        return users;
    }

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
		
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	
	public boolean getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	
	public User getCoach() {
		return coach;
	}

	public void setCoach(User coach) {
		this.coach = coach;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public String toString() {
		return String.format("%s - %s" , getName(), getDescription());
	}

}
