package controllers.account;

import controllers.BaseController;
import controllers.Secured;
import javastrava.api.v3.auth.model.Token;
import models.User;
import models.vm.Profile;
import models.vm.Settings;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;
import services.strava.StravaOAuth2Api;
import services.user.IUserService;
import services.user.UserService;
import views.html.userprofile;
import javax.inject.Inject;
import com.google.common.base.Strings;

/**
 * Profile controller
 * 
 * @author TEAM RMG
 *
 */
@Security.Authenticated(Secured.class)
public class ProfileController extends BaseController {
	
	@Inject 
	FormFactory formFactory;

    /**
     * Password Page. Ask the user to change his password.
     *
     * @return index settings
     */
	@Transactional
    public Result index() {
    	IUserService userService = new UserService(em());
        User user = userService.findByEmail(request().username());
        
        Profile profile = userService.getStravaProfile(user);
        if(profile == null){
        	flash("error", getMessage("profile.load.fail"));
        	//rediret to settings, so that the user can establish a connection
			redirect(routes.SettingsController.index());
        }
        Form<Profile> profileForm = formFactory.form(Profile.class).fill(profile);
        return ok(userprofile.render(profileForm));
    }
	
	
	/**
	 * Save settings
	 * 
	 * @return http response
	 */
	@Transactional
    public Result save() {
        try {
        	Form<Profile> profileForm = formFactory.form(Profile.class).bindFromRequest();
    		IUserService userService = new UserService(em());
            User user = userService.findByEmail(request().username());
            
            if(profileForm.hasErrors()){
            	//flash("error", "")
            	return badRequest(userprofile.render(profileForm));
            }
            Profile profile = profileForm.get();
            //write strava settings
            userService.setStravaProfile(user, profile);
            
            flash("success", getMessage("profile.saved"));
        } catch (Exception e) {
            Logger.error("Cannot save strava profile", e);
            flash("error", getMessage("error.technical"));
        }
        return index();
    }
}
