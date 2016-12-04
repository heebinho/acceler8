package controllers;

import java.util.List;

import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.Strava;
import models.User;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;
import services.user.IUserService;
import services.user.UserService;
import views.html.dashboard.*;


/**
 * 
 * 
 * @author TEAM RMG
 *
 */
@Security.Authenticated(Secured.class)
public class DashboardController extends BaseController {
	


	/**
     * Default action dashboard.
     */
    @Transactional
    public Result index() {
    	
    	String email = ctx().session().get("email");
    	
    	IUserService userService = new UserService(em());
    	User user = userService.findByEmail(email);
    	
    	Token token = userService.getStravaAccessToken(user);
    	if(token == null){
    		//we can't get data from strava without an access token. Request one.
    		return redirect(controllers.account.routes.SettingsController.index());
    	}
    	
    	StravaAthlete athlete = token.getAthlete();
    	
    	user.setStrava_id(athlete.getId());   	
    	userService.persistUser(user);
    	
    	
    	Strava strava = new Strava(token);
    	List<StravaActivity> activities = strava.listAllAuthenticatedAthleteActivities();
    	
    	return ok(index.render(athlete, activities , "aa"));
    }
    

}
