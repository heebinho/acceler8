package services.user;

import javastrava.api.v3.auth.model.Token;
import models.User;
import models.vm.Profile;

/**
 * User service.
 * 
 * @author TEAM RMG
 *
 */
public interface IUserService {
	
    /**
     * Find User by id
     *
     * @param id
     * @return User or null
     */
	User findById(int id);
	
    /**
     * Find User by email
     *
     * @param email The mail to lookup
     * @return User or null
     */
    User findByEmail(String email);
	
	/**
	 * Find user by strava athlete id.
	 * @param id
	 * @return User or null
	 */
	User findByAthleteId(int id);
	
    /**
     * Persist user
     * 
     * @param user to persist
     * @return User or null
     */
	User persistUser(User user);
	
    /**
     * Retrieves a user from a confirmation token.
     *
     * @param token the confirmation token
     * @return User if the confirmation token is found, null otherwise
     */
    User findByConfirmationToken(String token);
    
    
    /**
     * Get an access token
     * 
     * @return Token A Strava access token or null
     */
    Token getStravaAccessToken(User user);
    
    /**
     * Token exchange. Persist token.
     * 
     * @param user
     * @param code
     * @return User
     */
    User setStravaAccessToken(User user, String code);

    /**
     * Get Strava profile
     * 
     * @param user
     * @return Profile view model
     */
	Profile getStravaProfile(User user);

	
	/**
	 * Write profile settings back to strava
	 * 
	 * @param profile The new profile
	 */
	void setStravaProfile(User user, Profile profile) throws Exception;


	/**
	 * Deauthorize user
	 * 
	 * @param user to deauthorize
	 * @return User deauthorized
	 */
	User deauthorize(User user);
	
}
