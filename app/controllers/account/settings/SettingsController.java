package controllers.account.settings;

import controllers.BaseController;
import controllers.Secured;
import controllers.routes;
import javastrava.api.v3.auth.AuthorisationService;
import javastrava.api.v3.auth.impl.retrofit.AuthorisationServiceImpl;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.auth.ref.AuthorisationScope;
import models.User;
import models.vm.Settings;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;
import services.settings.SettingsReader;
import services.strava.StravaOAuth2Api;
import services.user.IUserService;
import services.user.UserService;
import views.html.thesettings;
import play.libs.mailer.MailerClient;
import javax.inject.Inject;
import com.google.common.base.Strings;

/**
 * 
 * 
 * @author TEAM RMG
 *
 */
@Security.Authenticated(Secured.class)
public class SettingsController extends BaseController {
	
	@Inject 
	FormFactory formFactory;
	
	@Inject
    MailerClient mailerClient;

    /**
     * Password Page. Ask the user to change his password.
     *
     * @return index settings
     */
	@Transactional
    public Result index() {
    	IUserService userService = new UserService(em());
        User user = userService.findByEmail(request().username());
        
        Settings settings = new Settings();
        settings.setStrava_token_public(user.getStrava_token_public());
        
        Token token = userService.getStravaAccessToken(user);
        
        settings.setAuthorized(token!=null);		
        
        
        
        String authorizationUrl = StravaOAuth2Api.getLink(
        		controllers.account.settings.routes.SettingsController.onauthorized("","","")
        		.absoluteURL(request()));
        settings.setAuthorizationUrl(authorizationUrl);
        
        Form<Settings> settingsForm = formFactory.form(Settings.class).fill(settings);
        return ok(thesettings.render(settingsForm));
    }
	

	
	
    /**
     * OAuth2 callback - when the user is coming back from the strava authentication/authorization dialog.
     * 
     * On success, a code will be included in the query string. 
     * If access is denied, error=access_denied will be included in the query string. 
     * In both cases, if provided, the state argument will also be included.
     * 
     * 
     * @param state 
     * @param code authorization code
     * @return
     */
	@Transactional
    public Result onauthorized(String state, String code, String error) {
    	
		if(!Strings.isNullOrEmpty(error))
			return onaccessdenied(error);
		
		String email = ctx().session().get("email");
    	IUserService userService = new UserService(em());
    	User user = userService.findByEmail(email);
    	user = userService.setStravaAccessToken(user, code);
    	
    	if(user == null){
    		flash("error", getMessage("error.technical"));
    	}
    	flash("success", getMessage("strava.tokenexchange.success"));
    	return index();
	}

	@Transactional
    public Result onaccessdenied(String error) {
		Logger.info("access denied " + error);
		flash("error", "Access denied");
		return index();
	}
	
	/**
	 * Save settings
	 * 
	 * @return http response
	 */
	@Transactional
    public Result save() {
		Form<Settings> settingsForm = formFactory.form(Settings.class).bindFromRequest();
		Settings settings = settingsForm.get();
		
    	if(!settings.getPassword().equals(settings.getPasswordConfirmation())){
    		flash("error", "Passwords do not match.");
			return index();
    	}
		        
        try {
    		IUserService userService = new UserService(em());
            User user = userService.findByEmail(request().username());
            
            if(!Strings.isNullOrEmpty(settings.getPassword())){
            	//update password if we have to
            	user.setPassword(settings.getPassword());
            }
            
            user.setStrava_token_public(settings.getStrava_token_public());
            userService.persistUser(user);
            
            flash("success", getMessage("settings.saved"));
        } catch (Exception e) {
            Logger.error("Cannot validate URL", e);
            flash("error", getMessage("error.technical"));
        }
        return index();
    }
}
