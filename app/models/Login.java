package models;

import play.data.validation.Constraints;


/**
 * Login Model
 * 
 * @author TEAM RMG
 * 
 */
public class Login {
	
    @Constraints.Required
    public String email;
    @Constraints.Required
    public String password;
    

     

}
