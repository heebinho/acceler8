/**
 * 
 */
package controllers;

import java.util.List;

import models.Team;
import models.User;
import models.dao.ITeamDao;
import models.dao.TeamDao;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;
import services.account.AccountService;
import services.account.IAccountService;
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
