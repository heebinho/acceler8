/**
 * 
 */
package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javastrava.api.v3.auth.TokenManager;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.auth.ref.AuthorisationScope;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.Strava;
import models.Team;
import models.User;
import models.dao.ITeamDao;
import models.dao.TeamDao;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;
import services.account.AccountService;
import services.account.IAccountService;
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
    	
    	String email = ctx().session().get("email");
    	IAccountService authService = new AccountService(em());
    	User user = authService.findByEmail(email);
    	
    	ITeamDao dao = new TeamDao(em());
    	List<Team> teams = dao.getTeamsByUser(user.getId());
    	
    	return ok(index.render(teams));
    	
    }
    
    @Transactional
    public Result details(int id) {
    	
    	String email = ctx().session().get("email");
    	
    	IAccountService authService = new AccountService(em());
    	User user = authService.findByEmail(email);
    	
    	ITeamService service = new TeamService(em());
    	Team team = service.findById(id);
    	
    	//try to get the token from the manager
    	Token token = TokenManager.instance()
    			.retrieveTokenWithScope(user.getEmail(), AuthorisationScope.VIEW_PRIVATE);
    	
    	boolean member = false;
    	Strava strava = new Strava(token);
    	List<StravaAthlete> athletes = new ArrayList<StravaAthlete>();
    	for (User teamMember : team.getUsers()) {
    		StravaAthlete athlete = strava.getAthlete(teamMember.getStrava_id());
    		athletes.add(athlete);
    		if(user.getId() == teamMember.getId())
    			member = true;
		}
    	
    	
    	
    	return ok(detail.render(member, team, athletes));
    }
    
    @Transactional
    public Result save() {return TODO;}
    
    @Transactional
    public Result delete(int id) {return TODO;}
    


}
