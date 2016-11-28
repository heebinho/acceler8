package services.user;

import models.User;

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
	
}
