package services.account;

import java.util.UUID;

import javax.persistence.EntityManager;

import models.Token;
import models.User;
import models.dao.ITokenDao;
import models.dao.IUserDao;
import models.dao.TokenDao;
import models.dao.UserDao;
import services.Service;
import services.crypt.HashHelper;

/**
 * Account services implementation. Authentication.
 * 
 * @author TEAM RMG
 *
 */
public class AccountService extends Service implements IAccountService {

	public AccountService(EntityManager em) {
		super(em);
	}

	/**
	 * Authenticate the user
	 * 
	 * @param email
	 * @param password
	 *            clear password candidate string
	 * @return User when authenticated, otherwise null
	 */
	@Override
	public User authenticate(String email, String password) {
		IUserDao dao = new UserDao(getEntityManager());
		User user = dao.findByEmail(email);
		if (user != null) {
			if (HashHelper.checkPassword(password, user.getPassword()))
				return user;
		}
		return null;
	}

	/**
	 * Confirms an account.
	 *
	 * @return User confirmed user
	 * @throws IllegalArgumentException
	 */
	@Override
	public User confirm(User user) throws IllegalArgumentException {
		if (user == null) {
			throw new IllegalArgumentException();
		}

		user.setValidated(true);
		IUserDao dao = new UserDao(getEntityManager());
		return dao.save(user);
	}

    /**
     * Retrieve a token by id and type.
     *
     * @param uuid token
     * @param type of token
     * @return Token reset token
     * @throws IllegalArgumentException
     */
	@Override
	public Token findByTokenAndType(String uuid, String type) {
		ITokenDao dao = new TokenDao(getEntityManager());
		return dao.findByTokenAndType(uuid, type);
	}

	/**
	 * Delete token
	 * 
	 * @param token
	 */
	@Override
	public void deleteToken(Token token) {
		ITokenDao dao = new TokenDao(getEntityManager());
		dao.delete(token);
	}

    /**
     * Return a new Token.
     *
     * @param user
     * @param type of token
     * @param email
     * @return Token new token
     */
	@Override
	public Token getNewToken(User user, String type, String email) {
		Token token = new Token();
		token.setToken(UUID.randomUUID().toString());
		token.setUserId(user.getId());
		token.setType(type);;
		token.setEmail(email);

		ITokenDao dao = new TokenDao(getEntityManager());
		return dao.save(token);
	}


}
