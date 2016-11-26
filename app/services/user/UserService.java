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

	@Override
	public User findById(int id) {
		IUserDao dao = new UserDao(getEntityManager());
		return dao.findById(id);
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



}
