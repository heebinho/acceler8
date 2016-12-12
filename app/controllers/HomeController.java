package controllers;

import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.*;
import services.account.AccountService;
import services.account.IAccountService;
import services.user.IUserService;
import services.user.UserService;
import views.html.*;
import javax.inject.Inject;

import models.User;
import models.vm.Login;
import models.vm.Signup;


/**
 * Home controller.
 * 
 * 
 * @author TEAM RMG
 * 
 */
public class HomeController extends BaseController {
	
	@Inject FormFactory formFactory;	
	
	/**
	 * Index action.
	 * Redirect to dashboard if authenticated, 
	 * otherwise render signup and login forms. 
	 * 
	 * @return Result http status.
	 */
	@Transactional
	public Result index() {
    	
        String email = ctx().session().get("email");
        if (email != null) {
        	IUserService service = new UserService(em());
        	User user = service.findByEmail(email);
            
            if (user != null && user.isValidated()) {
            	return redirect(routes.DashboardController.index());
            } else {
                Logger.debug("Clearing invalid session credentials");
                session().clear();
            }
        }

        return ok(index.render(
        		formFactory.form(Signup.class), 
        		formFactory.form(Login.class)));
    }
    
	
    /**
     * Authenticate action.
     * Handle login form submission.
     *
     * @return Result Dashboard if auth OK or login form if auth fails
     */
	@Transactional
    public Result authenticate() {
        
    	Form<Login> loginForm = formFactory.form(Login.class).bindFromRequest();
        Form<Signup> registerForm = formFactory.form(Signup.class);
        
        if(loginForm.hasErrors()){
        	return badRequest(index.render(registerForm, loginForm));
        }
        
        try {
        	Login login = loginForm.get();
            IAccountService accountService = new AccountService(em());
        	User user = accountService.authenticate(login.getEmail(), login.getPassword());	
            
        	if (user != null && user.isValidated()) {
        		session("email", loginForm.get().getEmail());
                return redirect(routes.DashboardController.index());
            } else if (user == null) {
            	flash("error", getMessage("invalid.user.or.password"));
            }else{
            	flash("error", getMessage("account.not.validated.check.mail"));
            }
            
        } catch (Exception e) {
        	flash("error", getMessage("error.technical"));
        }
        
        return badRequest(index.render(registerForm, loginForm));
    }

	
    /**
     * Logout action.
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
