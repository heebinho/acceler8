package models;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Base entity type. To be extended.
 * 
 * @author TEAM RMG
 */
@MappedSuperclass
public class Model {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
