package services.account;

import java.net.MalformedURLException;

import javax.annotation.Nullable;

import play.libs.mailer.MailerClient;
import models.Token;
import models.Token.TypeToken;
import models.User;

/**
 * Account service
 * 
 * @author TEAM RMG
 *
 */
public interface IAccountService {
	
	/**
	 * Authenticate
	 * 
	 * @param email
	 * @param password
	 * @return User when found, else null
	 */
	User authenticate(String email, String password);
	
    /**
     * Confirms an account.
     *
     * @return true if confirmed, false otherwise.
     * @throws AppException App Exception
     */
    boolean confirm(User user) throws Exception;

	/**
	 * 
	 * @param token
	 * @param email
	 * @return
	 */
	Token findByTokenAndType(String token, TypeToken type);

	/**
	 * Delete token
	 * 
	 * @param resetToken
	 */
	void deleteToken(Token resetToken);
	
    /**
     * Return a new Token.
     *
     * @param user  user
     * @param type  type of token
     * @param email email for a token change email
     * @return a reset token
     */
    Token getNewToken(User user, TypeToken type, String email);
    
    /**
     * Send the Email to confirm ask new password.
     *
     * @param user  the current user
     * @param type  token type
     * @param email email for a change email token
     * @throws java.net.MalformedURLException if token is wrong.
     */
    void sendMail(User user, TypeToken type, String email, MailerClient mc) throws MalformedURLException;

    /**
     * Send the Email to confirm ask new password.
     *
     * @param user the current user
     * @throws java.net.MalformedURLException if token is wrong.
     */
    void sendMailResetPassword(User user, MailerClient mc) throws MalformedURLException;

    /**
     * Send the Email to confirm ask new password.
     *
     * @param user  the current user
     * @param email email for a change email token
     * @throws java.net.MalformedURLException if token is wrong.
     */
    void sendMailChangeMail(User user, @Nullable String email,MailerClient mc) throws MalformedURLException;

}
