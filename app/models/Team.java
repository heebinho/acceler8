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
	

    public Set<User> getUsers() {
        return users;
    }

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	private String description;
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	private String city;
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	private String country;
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	private String website;
	
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	
	private boolean isPrivate = false;
	
	public boolean isPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	
	@Override
	public String toString() {
		return String.format("%s - %s", getId(), getName());
	}

}
