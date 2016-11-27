package controllers.account;

import models.Token;
import models.User;
import models.dao.ITokenDao;
import models.dao.IUserDao;
import models.dao.TokenDao;
import models.dao.UserDao;
import models.Mail;
import org.apache.commons.mail.EmailException;

import controllers.BaseController;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import play.db.jpa.Transactional;
import play.mvc.Result;
import services.account.AccountService;
import services.account.IAccountService;
import views.html.account.reset.ask;
import play.libs.mailer.MailerClient;

import javax.inject.Inject;

import views.html.account.reset.reset;
import views.html.account.reset.runAsk;

import java.net.MalformedURLException;



/**
 * Handle password resetting.
 * 
 * 
 * @author TEAM RMG
 *
 */
public class ResetController extends BaseController {
    
	@Inject
    MailerClient mailerClient;
	
	@Inject 
	FormFactory formFactory;

    public static class AskForm {
        @Constraints.Required
        public String email;
    }

    public static class ResetForm {
        @Constraints.Required
        public String inputPassword;
    }

    /**
     * Display the reset password form.
     *
     * @return reset password form
     */
    public Result ask() {
        Form<AskForm> askForm = formFactory.form(AskForm.class);
        return ok(ask.render(askForm));
    }

    /**
     * Run ask password.
     *
     * @return reset password form if error, runAsk render otherwise
     */
    @Transactional
    public Result runAsk() {
        Form<AskForm> askForm = formFactory.form(AskForm.class).bindFromRequest();

        if (askForm.hasErrors()) {
            flash("error", getMessage("signup.valid.email"));
            return badRequest(ask.render(askForm));
        }

        final String email = askForm.get().email;
        Logger.debug("runAsk: email = " + email);
        
        IUserDao dao = new UserDao(em());
        User user = dao.findByEmail(email);
        
        Logger.debug("runAsk: user = " + user);

        if (user == null) {
            Logger.debug("No user found with email " + email);
            //If we do not have this email address in the list, we should not expose this to the user.
            return ok(runAsk.render());
        }

        Logger.debug("Sending password reset link to user " + user);

        try {
            IAccountService accountService = new AccountService(em());
            accountService.sendMailResetPassword(user, mailerClient);
            return ok(runAsk.render());
        } catch (MalformedURLException e) {
            Logger.error("Cannot validate URL", e);
            flash("error", getMessage("error.technical"));
        }
        return badRequest(ask.render(askForm));
    }

    @Transactional
    public Result reset(String token) {

        if (token == null) {
            flash("error", getMessage("error.technical"));
            Form<AskForm> askForm = formFactory.form(AskForm.class);
            return badRequest(ask.render(askForm));
        }

        ITokenDao dao = new TokenDao(em());
        Token resetToken = dao.findByTokenAndType(token, Token.TypeToken.password);

        if (resetToken == null) {
            flash("error", getMessage("error.technical"));
            Form<AskForm> askForm = formFactory.form(AskForm.class);
            return badRequest(ask.render(askForm));
        }

        if (resetToken.isExpired()) {
            dao.delete(resetToken);
        	//resetToken.delete();
            flash("error", getMessage("error.expiredresetlink"));
            Form<AskForm> askForm = formFactory.form(AskForm.class);
            return badRequest(ask.render(askForm));
        }

        Form<ResetForm> resetForm = formFactory.form(ResetForm.class);
        return ok(reset.render(resetForm, token));
    }

    /**
     * @return reset password form
     */
    @Transactional
    public Result runReset(String token) {
        Form<ResetForm> resetForm = formFactory.form(ResetForm.class).bindFromRequest();

        if (resetForm.hasErrors()) {
            flash("error", getMessage("signup.valid.password"));
            return badRequest(reset.render(resetForm, token));
        }

        try {
            ITokenDao dao = new TokenDao(em());
            Token resetToken = dao.findByTokenAndType(token, Token.TypeToken.password);
            
            if (resetToken == null) {
                flash("error", getMessage("error.technical"));
                return badRequest(reset.render(resetForm, token));
            }

            if (resetToken.isExpired()) {
                dao.delete(resetToken);
                flash("error", getMessage("error.expiredresetlink"));
                return badRequest(reset.render(resetForm, token));
            }

            // check email
            IUserDao uDao = new UserDao(em());
            User user = uDao.findById(resetToken.userId);
            
            if (user == null) {
                // display no detail (email unknown for example) to
                // avoid check email by foreigner
                flash("error", getMessage("error.technical"));
                return badRequest(reset.render(resetForm, token));
            }

            String password = resetForm.get().inputPassword;
            user.setPassword(password);
            uDao.save(user);

            // Send email saying that the password has just been changed.
            sendPasswordChanged(user);
            flash("success", getMessage("resetpassword.success"));
            return ok(reset.render(resetForm, token));
        } catch (Exception e) {
            flash("error", getMessage("error.technical"));
            return badRequest(reset.render(resetForm, token));
        }

    }

    /**
     * Send mail with the new password.
     *
     * @param user user created
     * @throws EmailException Exception when sending mail
     */
    private void sendPasswordChanged(User user) throws EmailException {
        String subject = getMessage("mail.reset.confirm.subject");
        String message = getMessage("mail.reset.confirm.message");
        Mail.Envelop envelop = new Mail.Envelop(subject, message, user.getEmail());
        Mail mailer = new Mail(mailerClient);
        mailer.sendMail(envelop);
    }
}
