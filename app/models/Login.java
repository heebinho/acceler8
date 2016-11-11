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
    
    /**
     * Validate the authentication.
     *
     * @return null if validation ok, string with details otherwise
     */
    /*
    public String validate() {

        User user = null;
        try {
        	
            user = User.authenticate(email, password);
        } catch (Exception e) {
            return Messages.get("error.technical");
        }
        if (user == null) {
            return Messages.get("invalid.user.or.password");
        } else if (!user.validated) {
            return Messages.get("account.not.validated.check.mail");
        }
        return null;
    }
     */

}
