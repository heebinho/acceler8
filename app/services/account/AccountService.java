package services.account;

import play.libs.mailer.MailerClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;

import models.Mail;
import models.Token;
import models.Token.TypeToken;
import models.User;
import models.dao.ITokenDao;
import models.dao.IUserDao;
import models.dao.TokenDao;
import models.dao.UserDao;
import play.Configuration;
import play.Logger;
import play.i18n.Messages;
import services.Service;
import services.crypt.HashHelper;

/**
 * Implements Authentication
 * 
 * @author TEAM RMG
 *
 */
public class AccountService extends Service implements IAccountService {

	public AccountService(EntityManager em){
		super(em);
	}

	/**
	 * Authenticate the user
	 * 
	 * @return User when authenticated, else null
	 */
	@Override
	public User authenticate(String email, String password) {
		
		IUserDao dao = new UserDao(getEntityManager());
		User user = dao.findByEmail(email);
		if(user != null){
			if(HashHelper.checkPassword(password, user.getPassword()))
				return user;
		}
		return null;
	}

    /**
     * Confirms an account.
     *
     * @return true if confirmed, false otherwise.
     * @throws AppException App Exception
     */
	@Override
    public boolean confirm(User user) throws Exception {
        
		if (user == null) {
            return false;
        }

        user.setToken(null);
        user.setValidated(true);

        //persist
        IUserDao dao = new UserDao(getEntityManager());
        dao.save(user);

        return true;
    }
       
    /**
     * Find User by email
     *
     * @return User or null
     * 
     */
    @Override
    public User findByEmail(String email){
    	
    	IUserDao dao = new UserDao(getEntityManager());
    	return dao.findByEmail(email);
    }
    
    
    /**
     * Find User by id
     *
     * @return User or null
     * 
     */
    @Override
    public User findById(int userId) {
    	IUserDao dao = new UserDao(getEntityManager());
    	return dao.findById(userId);
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
	 * 
	 * 
	 * @param token
	 * @param email
	 * @return
	 */
	@Override
	public Token findByTokenAndType(String token, TypeToken type) {
		
		ITokenDao dao = new TokenDao(getEntityManager());
		return dao.findByTokenAndType(token, type);
	}

	@Override
	public void deleteToken(Token token) {
		
		ITokenDao dao = new TokenDao(getEntityManager());
		dao.delete(token);
		
	}

	
	/**
	 * Return a new Token.
	 *
	 * @param user  user
	 * @param type  type of token
	 * @param email email for a token change email
	 * @return a reset token
	 */
   public Token getNewToken(User user, TypeToken type, String email) {
       Token token = new Token();
       token.token = UUID.randomUUID().toString();
       token.userId = user.getId();
       token.type = type;
       token.email = email;
       
       ITokenDao dao = new TokenDao(getEntityManager());
       return dao.save(token);
   }

   /**
    * Send the Email to confirm ask new password.
    *
    * @param user  the current user
    * @param type  token type
    * @param email email for a change email token
    * @throws java.net.MalformedURLException if token is wrong.
    */
   public void sendMail(User user, TypeToken type, String email, MailerClient mc) throws MalformedURLException {

       Token token = getNewToken(user, type, email);
       String externalServer = Configuration.root().getString("server.hostname");

       String subject = null;
       String message = null;
       String toMail = null;

       // Should use reverse routing here.
       String urlString = "http://" + externalServer + "/" + type.getUrlPath() + "/" + token.token;
       URL url = new URL(urlString); // validate the URL

       switch (type) {
           case password:
               subject = Messages.get("mail.reset.ask.subject");
               message = Messages.get("mail.reset.ask.message", url.toString());
               toMail = user.getEmail();
               break;
           case email:
               subject = Messages.get("mail.change.ask.subject");
               message = Messages.get("mail.change.ask.message", url.toString());
               toMail = token.email; // == email parameter
               break;
       }

       Logger.debug("sendMailResetLink: url = " + url);
       Mail.Envelop envelop = new Mail.Envelop(subject, message, toMail);
       Mail mail = new Mail(mc);
       mail.sendMail(envelop);
   }
   
   /**
    * Send the Email to confirm ask new password.
    *
    * @param user the current user
    * @throws java.net.MalformedURLException if token is wrong.
    */
   public void sendMailResetPassword(User user, MailerClient mc) throws MalformedURLException {
   		sendMail(user, TypeToken.password, user.getEmail(), mc);
   }

   /**
    * Send the Email to confirm ask new password.
    *
    * @param user  the current user
    * @param email email for a change email token
    * @throws java.net.MalformedURLException if token is wrong.
    */
   public void sendMailChangeMail(User user, @Nullable String email,MailerClient mc) throws MalformedURLException {
       sendMail(user, TypeToken.email, email,mc );
   }
	

}
