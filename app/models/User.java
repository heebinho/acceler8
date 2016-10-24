/**
 * 
 */
package models;

/**
 * @author heebinho
 *
 */
public class User {
	
	public String mail;
	public String name;
	
	private void User() {
		
	}
	
	private void User(String mail) {
		this.mail = mail;
	}
	
	@Override
	public String toString() {
		return mail;
	}
	

}
