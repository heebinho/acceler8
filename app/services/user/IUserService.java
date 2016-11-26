package services.user;

import models.User;

/**
 * User service.
 * 
 * @author TEAM RMG
 *
 */
public interface IUserService {
	
	User findById(int id);
	
	/**
	 * Find user by strava athlete id.
	 * @param id
	 * @return
	 */
	User findByAthleteId(int id);
	
}
