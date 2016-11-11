package models;

import javax.persistence.*;

import play.data.validation.Constraints.*;


@Entity
public class Team extends Model {
	

	
	@Required
	private String name;
	
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

	public Team(){}
	
	public Team(String name, String description){
		this.name = name;
		this.description = description;
	}
	
	@Override
	public String toString() {
		return String.format("%s - %s", getId(), name);
	}
	

}
