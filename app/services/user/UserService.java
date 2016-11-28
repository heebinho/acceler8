package services.user;

import javax.persistence.EntityManager;

import com.google.common.base.Strings;

import javastrava.api.v3.auth.AuthorisationService;
import javastrava.api.v3.auth.TokenManager;
import javastrava.api.v3.auth.impl.retrofit.AuthorisationServiceImpl;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.auth.ref.AuthorisationScope;
import models.User;
import models.dao.IUserDao;
import models.dao.UserDao;
import play.Logger;
import services.Service;
import services.settings.SettingsReader;


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

    /**
     * Get an access token
     * 
     * @return Token A Strava access token or null
     */
	@Override
	public Token getStravaAccessToken(User user) {
    	//try to get the token from the manager
    	Token token = TokenManager.instance()
    			.retrieveTokenWithScope(user.getEmail(), AuthorisationScope.VIEW_PRIVATE, AuthorisationScope.WRITE);
    	
    	if(token == null && !Strings.isNullOrEmpty(user.getStrava_code())){
    		//try get a new token if we already have a code.
    		token = doTokenExchange(user.getStrava_code());
    		if(token !=null){
    			//store new token
    			user.setStrava_token(token.getToken());
    		}
    	}
    	
		return token;
	}

    /**
     * Token exchange. Persist token.
     * 
     * @param user
     * @param code
     * @return User
     */
	@Override
	public User setStravaAccessToken(User user, String code) {
    	
    	Token token = doTokenExchange(code);
    	if(token != null){
    		user.setStrava_code(code);
        	user.setStrava_token(token.getToken());
        	user.setStrava_id(token.getAthlete().getId());    		
    	}
    	return this.persistUser(user);
	}

	private Token doTokenExchange(String code){
		try{
			AuthorisationService service = new AuthorisationServiceImpl();
	    	Integer clientId = SettingsReader.getValue(SettingsReader.CLIENT_ID);
	    	String secret = SettingsReader.getKey(SettingsReader.CLIENT_SECRET);   	
	    	
    		Token token = service.tokenExchange(clientId, secret, code, 
    				AuthorisationScope.VIEW_PRIVATE, AuthorisationScope.WRITE);
    		return token;
    	}catch(Exception any){
    		Logger.error(any.getMessage());
    		return null;
    	}
	}

}
