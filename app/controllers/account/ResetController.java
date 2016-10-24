package controllers.account;

import models.Token;
import models.User;
import models.dao.ITokenDao;
import models.dao.IUserDao;
import models.dao.TokenDao;
import models.dao.UserDao;
import models.Mail;
import models.vm.EMail;
import models.vm.Login;
import models.vm.Password;
import models.vm.Signup;

import org.apache.commons.mail.EmailException;

import controllers.BaseController;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Result;
import services.account.AccountService;
import services.account.IAccountService;
import views.html.index;
import views.html.account.reset.ask;
import play.libs.mailer.MailerClient;

import javax.inject.Inject;

import views.html.account.reset.reset;

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


    /**
     * Display the reset password form.
     *
     * @return reset password form
     */
    public Result ask() {
        Form<EMail> askForm = formFactory.form(EMail.class);
        return ok(ask.render(askForm));
    }

    /**
     * Run ask password.
     *
     * @return reset password form if error, runAsk render otherwise
     */
    @Transactional
    public Result runAsk() {
        Form<EMail> askForm = formFactory.form(EMail.class).bindFromRequest();

        if (askForm.hasErrors()) {
            flash("error", getMessage("signup.valid.email"));
            return badRequest(ask.render(askForm));
        }

        final String email = askForm.get().getEmail();
        Logger.debug("runAsk: email = " + email);
        
        IUserDao dao = new UserDao(em());
        User user = dao.findByEmail(email);
        
        Logger.debug("runAsk: user = " + user);

        if (user == null) {
            Logger.debug("No user found with email " + email);
            flash("error", getMessage("resetpassword.notfound"));
            return badRequest(ask.render(formFactory.form(EMail.class)));
        }

        Logger.debug("Sending password reset link to user " + user);

        try {
            IAccountService accountService = new AccountService(em());
            accountService.sendMailResetPassword(user, mailerClient);
            flash("success", getMessage("resetpassword.mailsent"));
            return ok(index.render(
            		formFactory.form(Signup.class), 
            		formFactory.form(Login.class)));
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
            Form<EMail> askForm = formFactory.form(EMail.class);
            return badRequest(ask.render(askForm));
        }

        ITokenDao dao = new TokenDao(em());
        Token resetToken = dao.findByTokenAndType(token, Token.TypeToken.password);

        if (resetToken == null) {
            flash("error", getMessage("error.technical"));
            Form<EMail> askForm = formFactory.form(EMail.class);
            return badRequest(ask.render(askForm));
        }

        if (resetToken.isExpired()) {
            dao.delete(resetToken);
        	//resetToken.delete();
            flash("error", getMessage("error.expiredresetlink"));
            Form<EMail> askForm = formFactory.form(EMail.class);
            return badRequest(ask.render(askForm));
        }

        Form<Password> resetForm = formFactory.form(Password.class);
        return ok(reset.render(resetForm, token));
    }

    /**
     * @return reset password form
     */
    @Transactional
    public Result runReset(String token) {
        Form<Password> resetForm = formFactory.form(Password.class).bindFromRequest();

        if (resetForm.hasErrors()) {
            flash("error", getMessage("resetpassword.not.valid.password"));
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

            String password = resetForm.get().getPassword();
            user.setPassword(password);
            uDao.save(user);

            // Send email saying that the password has just been changed.
            sendPasswordChanged(user);
            flash("success", getMessage("resetpassword.success"));
            return ok(index.render(
            		formFactory.form(Signup.class), 
            		formFactory.form(Login.class)));
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
