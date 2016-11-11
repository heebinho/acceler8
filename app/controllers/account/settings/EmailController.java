package controllers.account.settings;

import controllers.Secured;
import models.Token;
import models.User;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import play.db.jpa.JPA;
import play.db.jpa.JPAApi;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.account.AccountService;
import services.account.IAccountService;
import views.html.account.settings.email;
import views.html.account.settings.emailValidate;
import play.libs.mailer.MailerClient;

import javax.inject.Inject;
import java.net.MalformedURLException;

import static play.data.Form.form;

/**
 * Settings -> Email page.
 * <p/>
 * User: yesnault
 * Date: 23/06/12
 */
@Security.Authenticated(Secured.class)
public class EmailController extends Controller {
    
	JPAApi jpa;

	/**
	 * ctor 
	 * @param api java persistence api (injected)
	 */
	@Inject
	public EmailController(JPAApi api) {
	    this.jpa = api;
	}
	
	@Inject
    MailerClient mailerClient;

    public static class AskForm {
        @Constraints.Required
        public String email;

        public AskForm() {
        }

        AskForm(String email) {
            this.email = email;
        }
    }

    /**
     * Password Page. Ask the user to change his password.
     *
     * @return index settings
     */
    public Result index() {
    	
        //User user = User.findByEmail(request().username());
        User user = new AccountService(jpa.em()).findByEmail(request().username());
        
        Form<AskForm> askForm = form(AskForm.class);
        askForm = askForm.fill(new AskForm(user.getEmail()));
        return ok(email.render(user, askForm));
    }

    /**
     * Send a mail to confirm.
     *
     * @return email page with flash error or success
     */
    public Result runEmail() {
        Form<AskForm> askForm = form(AskForm.class).bindFromRequest();
        //User user = User.findByEmail(request().username());
        User user = new AccountService(jpa.em()).findByEmail(request().username());

        if (askForm.hasErrors()) {
            flash("error", Messages.get("signup.valid.email"));
            return badRequest(email.render(user, askForm));
        }

        try {
            String mail = askForm.get().email;
            Token t = new Token();
            
            IAccountService accountService = new AccountService(jpa.em());
            accountService.sendMailChangeMail(user, mail, mailerClient);
            
            flash("success", Messages.get("changemail.mailsent"));
            return ok(email.render(user, askForm));
        } catch (MalformedURLException e) {
            Logger.error("Cannot validate URL", e);
            flash("error", Messages.get("error.technical"));
        }
        return badRequest(email.render(user, askForm));
    }

    /**
     * Validate a email.
     *
     * @return email page with flash error or success
     */
    public Result validateEmail(String token) {
        //User user = User.findByEmail(request().username());
    	IAccountService accountService = new AccountService(jpa.em()); 
    	User user = accountService.findByEmail(request().username());

        if (token == null) {
            flash("error", Messages.get("error.technical"));
            return badRequest(emailValidate.render(user));
        }

        Token resetToken = accountService.findByTokenAndType(token, Token.TypeToken.email);
        if (resetToken == null) {
            flash("error", Messages.get("error.technical"));
            return badRequest(emailValidate.render(user));
        }

        if (resetToken.isExpired()) {
            accountService.deleteToken(resetToken);
            
            flash("error", Messages.get("error.expiredmaillink"));
            return badRequest(emailValidate.render(user));
        }

        user.setEmail(resetToken.email);
        accountService.persistUser(user);
        

        session("email", resetToken.email);

        flash("success", Messages.get("account.settings.email.successful", user.getEmail()));

        return ok(emailValidate.render(user));
    }
}
