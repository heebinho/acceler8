package controllers;

import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.*;
import services.account.AccountService;
import services.account.IAccountService;
import views.html.*;
import javax.inject.Inject;

import models.Login;
import models.Register;
import models.User;
import models.dao.UserDao;


/**
 * 
 * 
 * @author TEAM RMG
 */
public class HomeController extends BaseController {
	
	@Inject FormFactory formFactory;	
	
	/**
	 * 
	 * @return http response
	 */
	@Transactional
	public Result index() {
    	
        // Check that the email matches a confirmed user before we redirect
        String email = ctx().session().get("email");
        if (email != null) {
            
        	UserDao dao = new UserDao(jpa.em());
        	User user = dao.findByEmail(email);
            
            if (user != null && user.isValidated()) {
            	return redirect(routes.DashboardController.index());
            } else {
                //Logger.debug("Clearing invalid session credentials");
                session().clear();
            }
        }

        return ok(index.render(
        		formFactory.form(Register.class), 
        		formFactory.form(Login.class)));
    }
    
    

    /**
     * Handle login form submission.
     *
     * @return Dashboard if auth OK or login form if auth KO
     */
	@Transactional
    public Result authenticate() {
        
    	Form<Login> loginForm = formFactory.form(Login.class).bindFromRequest();
        Form<Register> registerForm = formFactory.form(Register.class);
        Login login = loginForm.get();
        IAccountService accountService = new AccountService(em());
        
        try {
        	User user = accountService.authenticate(login.email, login.password);	
            
        	if (user != null && user.isValidated()) {
        		session("email", loginForm.get().email);
                return redirect(routes.DashboardController.index());
            } else if (user == null) {
            	flash("error", "invalid.user.or.password");
            }else{
            	flash("error", "account.not.validated.check.mail");
            }
            
        } catch (Exception e) {
        	flash("error", getMessage("error.technical"));
        }
        
        return badRequest(index.render(registerForm, loginForm));
    }

    
    /**
     * Logout and clean the session.
     *
     * @return Index page
     */
    public Result logout() {
        session().clear();
        flash("success", getMessage("youve.been.logged.out"));
        return redirect(routes.HomeController.index());
    }
    
    


}
