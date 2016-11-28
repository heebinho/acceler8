package services.user;

import javax.persistence.EntityManager;

import models.User;
import models.dao.IUserDao;
import models.dao.UserDao;
import services.Service;


/**
 * User services.
 * 
 * @author TEAM RMG
 *
 */
public class UserService extends Service implements IUserService {

	public UserService(EntityManager em){
		super(em);
	}

    /**
     * Find User by id
     *
     * @param id
     * @return User or null
     */
	@Override
	public User findById(int id) {
		IUserDao dao = new UserDao(getEntityManager());
		return dao.findById(id);
	}
	
    /**
     * Find User by email
     *
     * @param email
     * @return User or null
     */
    @Override
    public User findByEmail(String email){
    	IUserDao dao = new UserDao(getEntityManager());
    	return dao.findByEmail(email);
    }

	/**
	 * Find user by strava athlete id.
	 * @param id
	 * @return
	 */
	@Override
	public User findByAthleteId(int id) {
		IUserDao dao = new UserDao(getEntityManager()); 
		return dao.findByAthleteId(id);
	}
	
    /**
     * Persist user
     * 
     * @param user to persist
     * @return User or null
     */
	@Override
	public User persistUser(User user) {
		IUserDao dao = new UserDao(getEntityManager());
		return dao.save(user);
	}

    /**
     * Retrieves a user from a confirmation token.
     *
     * @param token the confirmation token
     * @return User if the confirmation token is found, null otherwise
     */
	@Override
	public User findByConfirmationToken(String token) {
		IUserDao dao = new UserDao(getEntityManager());
		return dao.findByConfirmationToken(token);
	}



}
