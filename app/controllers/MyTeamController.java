/**
 * 
 */
package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import controllers.account.ResetController.AskForm;
import javastrava.api.v3.auth.TokenManager;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.auth.ref.AuthorisationScope;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.Strava;
import models.Invite;
import models.Team;
import models.User;
import models.dao.ITeamDao;
import models.dao.TeamDao;
import play.data.Form;
import play.data.FormFactory;
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
     


}
