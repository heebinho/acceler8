package controllers.account.settings;

import controllers.BaseController;
import controllers.Secured;
import models.User;
import play.Logger;
import play.db.jpa.JPAApi;
import play.mvc.Result;
import play.mvc.Security;
import services.account.AccountService;
import services.account.IAccountService;
import services.user.IUserService;
import services.user.UserService;
import views.html.account.settings.password;
import play.libs.mailer.MailerClient;

import javax.inject.Inject;

import java.net.MalformedURLException;

/**
 * 
 * 
 * @author TEAM RMG
 *
 */
@Security.Authenticated(Secured.class)
public class PasswordController extends BaseController {

	JPAApi jpa;
	
	@Inject
    MailerClient mailerClient;

    /**
     * Password Page. Ask the user to change his password.
     *
     * @return index settings
     */
    public Result index() {
    	IUserService userService = new UserService(em());
        User user = userService.findByEmail(request().username());
    	return ok(password.render(user));
    }

    /**
     * Send a mail with the reset link.
     *
     * @return password page with flash error or success
     */
    public Result runPassword() {

    	IUserService userService = new UserService(em());
        User user = userService.findByEmail(request().username());
        
        try {
        	IAccountService accountService = new AccountService(em());	
            accountService.sendMailResetPassword(user, mailerClient);
            
            flash("success", getMessage("resetpassword.mailsent"));
            return ok(password.render(user));
        } catch (MalformedURLException e) {
            Logger.error("Cannot validate URL", e);
            flash("error", getMessage("error.technical"));
        }
        return badRequest(password.render(user));
    }
}
