package controllers.account.settings;

import controllers.BaseController;
import controllers.Secured;
import models.Token;
import models.User;
import models.vm.EMail;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import play.mvc.Result;
import play.mvc.Security;
import services.account.AccountService;
import services.account.IAccountService;
import views.html.account.settings.email;
import views.html.account.settings.emailValidate;
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
public class EmailController extends BaseController {
    
	@Inject
	FormFactory formFactory;
	
	@Inject
    MailerClient mailerClient;

    /**
     * Password Page. Ask the user to change his password.
     *
     * @return index settings
     */
    public Result index() {
    	
        IAccountService accountService = new AccountService(em());
        User user = accountService.findByEmail(request().username());
        
        Form<EMail> askForm = formFactory.form(EMail.class);
        askForm = askForm.fill(new EMail(user.getEmail()));
        return ok(email.render(user, askForm));
    }

    /**
     * Send a mail to confirm.
     *
     * @return email page with flash error or success
     */
    public Result runEmail() {
    	
        Form<EMail> askForm = formFactory.form(EMail.class).bindFromRequest();
        User user = new AccountService(em()).findByEmail(request().username());

        if (askForm.hasErrors()) {
            flash("error", getMessage("signup.valid.email"));
            return badRequest(email.render(user, askForm));
        }

        try {
            String mail = askForm.get().getEmail();
            
            IAccountService accountService = new AccountService(em());
            accountService.sendMailChangeMail(user, mail, mailerClient);
            
            flash("success", getMessage("changemail.mailsent"));
            return ok(email.render(user, askForm));
        } catch (MalformedURLException e) {
            Logger.error("Cannot validate URL", e);
            flash("error", getMessage("error.technical"));
        }
        return badRequest(email.render(user, askForm));
    }

    /**
     * Validate a email.
     *
     * @return email page with flash error or success
     */
    public Result validateEmail(String token) {
    	
    	IAccountService accountService = new AccountService(em()); 
    	User user = accountService.findByEmail(request().username());

        if (token == null) {
            flash("error", getMessage("error.technical"));
            return badRequest(emailValidate.render(user));
        }

        Token resetToken = accountService.findByTokenAndType(token, Token.TypeToken.email);
        if (resetToken == null) {
            flash("error", getMessage("error.technical"));
            return badRequest(emailValidate.render(user));
        }

        if (resetToken.isExpired()) {
            accountService.deleteToken(resetToken);
            
            flash("error", getMessage("error.expiredmaillink"));
            return badRequest(emailValidate.render(user));
        }

        user.setEmail(resetToken.email);
        accountService.persistUser(user);
        

        session("email", resetToken.email);

        flash("success", getMessage("account.settings.email.successful", user.getEmail()));

        return ok(emailValidate.render(user));
    }
}
