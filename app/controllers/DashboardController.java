/**
 * 
 */
package controllers;

import javastrava.api.v3.auth.TokenManager;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.auth.ref.AuthorisationScope;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.Strava;
import play.mvc.Controller;
import play.mvc.Result;
import services.ResourceService;
import views.html.dashboard;
import views.html.index;
import views.html.main;
//import views.html.dashboard;

/**
 * @author heebinho
 *
 */
public class DashboardController extends Controller {
	
    /**
     * Default action dashboard.
     */
    public Result index() {
    	
    	/*
    	Token token = TokenManager.instance()
    			.retrieveTokenWithScope("renatoheeb@gmail.com", AuthorisationScope.VIEW_PRIVATE);
    	
    	
    	Strava strava = new Strava(token);
    	StravaAthlete athlete = strava.getAthlete(15283400);
    	*/
    	//athlete.getCity()
    	
    	return ok(dashboard.render("this is going to rock"));
    }

}
