/**
 * 
 */
package controllers;

import java.util.List;

import models.Team;
import models.dao.ITeamDao;
import models.dao.TeamDao;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;
import services.team.ITeamService;
import services.team.TeamService;
import views.html.myteam.*;


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
    
    @Transactional
    public Result details(int id) {
    	
    	ITeamService service = new TeamService(em());
    	Team team = service.findById(id);
    	return ok(detail.render(team));
    }
    
    @Transactional
    public Result save() {return TODO;}
    
    @Transactional
    public Result delete(int id) {return TODO;}
    


}
