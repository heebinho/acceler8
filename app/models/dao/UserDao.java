package models.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import play.Logger;

import models.User;

/**
 * 
 * 
 * @author TEAM RMG
 */
public class UserDao extends Dao<User, Integer> implements IUserDao {

	public UserDao(EntityManager em) {
		super(User.class, em);
	}


	/**
	 * Find a user by a given email address
	 * 
	 * @param email email address
	 * @return User entity
	 */
	@Override
	public User findByEmail(String email) throws IllegalArgumentException {
		
		if(email == null)
			throw new IllegalArgumentException("empty.email");
		
		User user = null;
		
		Query query = getEntityManager().createQuery("select u from " + getPersistentClass().getSimpleName()
				+ " u where u.email = :email").setParameter("email", email);
		try {
			user = (User)query.getSingleResult();
		} catch (Exception e) {
			// do nothing (return null when not found)
		}
		
		return user;
	}
	
    /**
     * Retrieves a user from a confirmation token.
     *
     * @param token the confirmation token to use.
     * @return a user if the confirmation token is found, null otherwise.
     */
    public User findByConfirmationToken(String token) {
        
		if(token == null)
			throw new IllegalArgumentException("empty.token");
		
		User user = null;
		
		Query query = getEntityManager().createQuery("select u from " + getPersistentClass().getSimpleName()
				+ " u where u.token = :token").setParameter("token", token);
		try {
			user = (User)query.getSingleResult();
		} catch (Exception e) {
			// do nothing (return null when not found)
		}
		
		return user;
    }


    /**
     * Retrieves a user from an athelte id.
     *
     * @param strava athlete id
     * @return a user if the athlete is found, null otherwise.
     */
	@Override
	public User findByAthleteId(Integer athleteId) {
		
		if(athleteId == null)
			throw new IllegalArgumentException("no athelte id");
		
		User user = null;
		
		Query query = getEntityManager().createQuery("select u from " + getPersistentClass().getSimpleName()
				+ " u where u.strava_id = :id").setParameter("id", athleteId);
		try {
			user = (User)query.getSingleResult();
		} catch (Exception e) {
			Logger.error(e.getMessage());
		}
		
		return user;
	}


}
