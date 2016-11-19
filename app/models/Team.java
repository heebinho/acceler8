package models;

import java.util.HashSet;
import java.util.List;
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
	
	
	@Required
	private String name;
	
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
	
	public Team(){}

}
