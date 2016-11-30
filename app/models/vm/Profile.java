package models.vm;

import javastrava.api.v3.model.reference.StravaGender;

/**
 * Profile view model.
 * 
 * @author TEAM RMG
 *
 */
public class Profile {
    
	
	private String fullname;
    
    private String city;
    
    private String country;
    
    private String sex;
    
    private float weight;


	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
    
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

    
}
