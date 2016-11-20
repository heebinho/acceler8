/**
 * 
 */
package controllers;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.prism.ReadbackRenderTarget;

import javastrava.api.v3.auth.AuthorisationService;
import javastrava.api.v3.auth.TokenManager;
import javastrava.api.v3.auth.impl.retrofit.AuthorisationServiceImpl;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.auth.ref.AuthorisationScope;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.Strava;
import models.Team;
import models.User;
import models.dao.ITeamDao;
import models.dao.TeamDao;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Http.Cookie;
import services.settings.SettingsReader;
import services.strava.StravaOAuth2Api;
import views.html.myteam.*;
import services.account.*;


/**
 * My team manangement.
 * 
 * @author TEAM RMG
 *
 */
@Security.Authenticated(Secured.class)
public class MyTeamController extends BaseController {
	


	/**
     * Default action dashboard.
     */
    @Transactional
    public Result index() {
    	
    	ITeamDao dao = new TeamDao(em());
    	List<Team> teams = dao.getTeamsByUser(22);
    	
    	return ok(index.render(teams));
    	
    	/*
    	String email = ctx().session().get("email");
    	
    	IAccountService authService = new AccountService(em());
    	User user = authService.findByEmail(email);
    	
    	if(user.getStrava_code() == null || user.getStrava_token() == null){
    		//first attempt to get strava access token...
    		return redirect(StravaOAuth2Api.getLink());
    	}
    	
    	
    	//try to get the token from the manager
    	Token token = TokenManager.instance()
    			.retrieveTokenWithScope(user.getEmail(), AuthorisationScope.VIEW_PRIVATE);
    	
    	if(token == null){
    		token = getToken(user.getStrava_code());
    	}
    		
    	
    	Strava strava = new Strava(token);
    	StravaAthlete athlete = strava.getAthlete(15283400);
    	//athlete.getCreatedAt()
    	
    	
    	List<StravaActivity> activities = strava.listAllAuthenticatedAthleteActivities();
    	
    	return ok(index.render(token.getAthlete(), activities , "aa"));
    	*/
    	
    }
    
	/**
     * Default action dashboard.
     */
    @Transactional
    public Result details(int id) {return TODO;}
    
    @Transactional
    public Result save() {return TODO;}
    
    @Transactional
    public Result delete(int id) {return TODO;}
    


}
