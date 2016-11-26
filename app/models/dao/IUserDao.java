package models.dao;


import models.User;

/**
 * 
 * 
 * @author Team RMG
 */
public interface IUserDao extends IDao<User, Integer> {

	
	
	/**
	 * Retrieves a user from the mail address
	 * 
	 * @param email email to lookup.
	 * @return User if found, null otherwise.
	 */
	User findByEmail(String email);
	
	
    /**
     * Retrieves a user from a confirmation token.
     *
     * @param token the confirmation token to use.
     * @return a user if the confirmation token is found, null otherwise.
     */
    User findByConfirmationToken(String token);
    
    /**
     * Retrieves a user from an athelte id.
     *
     * @param strava athlete id
     * @return a user if the athlete is found, null otherwise.
     */
    User findByAthleteId(Integer athleteId);
	
}
