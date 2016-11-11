package controllers;

import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.*;
import play.mvc.Http.Cookie;
import services.account.AccountService;
import services.account.IAccountService;
import services.settings.Reader;
import services.strava.StravaOAuth2Api;
import views.html.*;
import views.html.defaultpages.todo;

import static play.data.Form.form;

import javax.inject.Inject;

import org.apache.logging.log4j.core.appender.routing.Routes;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;

import javastrava.api.v3.auth.AuthorisationService;
import javastrava.api.v3.auth.impl.retrofit.AuthorisationServiceImpl;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.auth.ref.AuthorisationScope;
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
            	//try to get strava access --> the access code, which we can use to get a token
            	if(user.getStrava_code() == null){
            		return redirect(StravaOAuth2Api.getLink());
            	}
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
    public Result authenticate() {
        
    	Form<Login> loginForm = formFactory.form(Login.class).bindFromRequest();
        Form<Register> registerForm = formFactory.form(Register.class);

        if (loginForm.hasErrors()) {
            return badRequest(index.render(registerForm, loginForm));
        } else {
            session("email", loginForm.get().email);
            return redirect(routes.DashboardController.index());
        }
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
