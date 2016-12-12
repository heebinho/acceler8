package services.account;

import models.Token;
import models.User;

/**
 * Account services interface.
 * Authentication.
 * 
 * @author TEAM RMG
 *
 */
public interface IAccountService {
	
	/**
	 * Authenticate the user
	 * 
	 * @param email
	 * @param password clear password candidate string
	 * @return User when authenticated, otherwise null
	 */
	User authenticate(String email, String password);
	
    /**
     * Confirms an account.
     *
     * @return User confirmed user
     * @throws IllegalArgumentException 
     */
    User confirm(User user) throws Exception;

    /**
     * Retrieve a token by id and type.
     *
     * @param uuid token
     * @param type of token
     * @return Token reset token
     * @throws IllegalArgumentException
     */
	Token findByTokenAndType(String uuid, String type);

	/**
	 * Delete token
	 * 
	 * @param token
	 */
	void deleteToken(Token token);
	
    /**
     * Return a new Token.
     *
     * @param user
     * @param type of token
     * @param email
     * @return Token new token
     */
    Token getNewToken(User user, String type, String email);


}
