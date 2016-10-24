package controllers.account;

import models.User;
import models.vm.Login;
import models.vm.Signup;
import models.Mail;

import org.apache.commons.mail.EmailException;

import controllers.BaseController;
import play.Configuration;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Result;
import services.account.AccountService;
import services.account.IAccountService;
import services.user.IUserService;
import services.user.UserService;
import play.libs.mailer.MailerClient;

import javax.inject.Inject;

import views.html.index;

import java.net.MalformedURLException;
import java.util.UUID;

/**
 * 
 * Signup
 * 
 * @author TEAM RMG
 *
 */
public class SignupController extends BaseController {
    
	
	@Inject
    MailerClient mailerClient;
	
	@Inject 
	FormFactory formFactory;	

    /**
     * Save the new user.
     *
     * @return Successfull page or created form if bad
     */
    @Transactional
    public Result save() {
        Form<Signup> registerForm = formFactory.form(Signup.class).bindFromRequest();

        if (registerForm.hasErrors()) {
            return badRequest(index.render(registerForm, formFactory.form(Login.class)));
        }

        Signup register = registerForm.get();
        IUserService userService = new UserService(em());
    	
        if (userService.findByEmail(register.getEmail()) != null) {
            flash("error", getMessage("error.email.already.exist"));
            return badRequest(index.render(registerForm, formFactory.form(Login.class)));
        }

       
        try {
        	User user = new User();
            user.setEmail(register.getEmail());
            user.setPassword(register.getPassword());
            user.setToken(UUID.randomUUID().toString());
            userService.persistUser(user);
            
            sendConfirmationMail(user);
            flash("success", getMessage("signup.successfull") + " " + getMessage("signup.msg.created"));
            
            return ok(index.render(formFactory.form(Signup.class), formFactory.form(Login.class)));
        } catch (EmailException e) {
            Logger.debug("Signup.save Cannot send email", e);
            flash("error", getMessage("error.sending.email"));
        } catch (Exception e) {
            Logger.error("Signup.save error", e);
            flash("error", getMessage("error.technical"));
        }
        
        return badRequest(index.render(registerForm, formFactory.form(Login.class)));
    }


    /**
     * Send the welcome Email with the link to confirm.
     *
     * @param user user created
     * @throws EmailException Exception when sending mail
     */
    private void sendConfirmationMail(User user) throws EmailException, MalformedURLException {
        String subject = getMessage("mail.confirm.subject");

        String urlString = "http://" + Configuration.root().getString("server.hostname"); //request().host();
        urlString += "/confirm/" + user.getToken();
        
        String message = getMessage("mail.confirm.message", urlString);

        Mail.Envelop envelop = new Mail.Envelop(subject, message, user.getEmail());
        Mail mailer = new Mail(mailerClient);
        mailer.sendMail(envelop);
    }

    /**
     * Valid an account with the url in the confirm mail.
     *
     * @param token a token attached to the user we're confirming.
     * @return Confirmationpage
     */
    @Transactional
    public Result confirm(String token) {
    	
    	IUserService userService = new UserService(em());
    	User user = userService.findByConfirmationToken(token);
        
        if (user == null) {
            flash("error", getMessage("error.unknown.token"));
            
            return badRequest(index.render(
            		formFactory.form(Signup.class), 
            		formFactory.form(Login.class)));
        }

        if (user.isValidated()) {
            flash("error", getMessage("error.account.already.validated"));
            return badRequest(index.render(
            		formFactory.form(Signup.class), 
            		formFactory.form(Login.class)));
        }

        try {
        	IAccountService accountService = new AccountService(em());
            if (accountService.confirm(user)) {
                sendMailConfirmation(user);
                flash("success", getMessage("account.successfully.validated"));
                return ok(index.render(
                		formFactory.form(Signup.class), 
                		formFactory.form(Login.class)));
            } else {
                Logger.debug("Signup.confirm cannot confirm user");
                flash("error", getMessage("error.confirm"));
                return badRequest(index.render(
                		formFactory.form(Signup.class), 
                		formFactory.form(Login.class)));
            }
        } catch (EmailException e) {
            Logger.debug("Cannot send email", e);
            flash("error", getMessage("error.sending.confirm.email"));
        } catch (Exception e) {
            Logger.debug("technical exception", e);
            flash("error", getMessage("error.sending.confirm.email"));
        }
        return badRequest(index.render(
        		formFactory.form(Signup.class), 
        		formFactory.form(Login.class)));
    }

    /**
     * Send the confirm mail.
     *
     * @param user user created
     * @throws EmailException Exception when sending mail
     */
    private void sendMailConfirmation(User user) throws EmailException {
        String subject = getMessage("mail.welcome.subject");
        String message = getMessage("mail.welcome.message");
        Mail.Envelop envelop = new Mail.Envelop(subject, message, user.getEmail());
        Mail mailer = new Mail(mailerClient);
        mailer.sendMail(envelop);
    }
}
