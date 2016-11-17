package models;

import java.util.List;

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
	
	
	@Required
	private String name;
	
	@ManyToMany
	private List<User> users;
	
	public List<User> getUsers() {
		return users;
		}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String description;
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public Team(String name, String description){
		this.name = name;
		this.description = description;
	}
	
	@Override
	public String toString() {
		return String.format("%s - %s", getId(), getName());
	}
	

}
