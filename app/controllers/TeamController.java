package controllers;

import play.mvc.*;
import services.account.AccountService;
import services.account.IAccountService;
import services.team.ITeamService;
import services.team.TeamService;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import javastrava.api.v3.auth.TokenManager;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.auth.ref.AuthorisationScope;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.Strava;
import models.Team;
import models.User;
import models.dao.TeamDao;
import views.html.team.*;




/**
 * Controls team requests
 * 
 * @author Team RMG
 */
public class TeamController extends BaseController {
	
	@Inject FormFactory formFactory;	

	
    /**
     * List all teams
     * 
     * @return http response
     */
	@Transactional()
    public Result index() {
		
		TeamDao dao = new TeamDao(jpa.em());
		List<Team> teams = dao.findAll();
		
    	return ok(list.render(teams));
    }

	@Transactional()
    public Result leave(int teamId) { 
		String email = ctx().session().get("email");
    	IAccountService authService = new AccountService(em());
    	User user = authService.findByEmail(email);
    	ITeamService teamService = new TeamService(em());
    	teamService.removeMember(user, teamId);
    	
		return redirect(routes.TeamController.index());
    }
	
	@Transactional()
    public Result join(int id) {
		
    	String email = ctx().session().get("email");
    	IAccountService authService = new AccountService(em());
    	User user = authService.findByEmail(email);
    	ITeamService teamService = new TeamService(em());
    	
    	if(teamService.isAlreadyMember(user, id))
    		return redirect(routes.MyTeamController.details(id));
    	
    	if(teamService.addNewMember(user, id)){
    		
    	}else{
    		//
    	}
    	
		
    	return redirect(routes.MyTeamController.details(id));
    }
	
	@Transactional()
    public Result remove(int teamid) {
	
			String email = ctx().session().get("email");
	    	IAccountService authService = new AccountService(em());
	    	User user = authService.findByEmail(email);
	    	
	    	ITeamService service = new TeamService(em());
	    	Team team = service.findById(teamid);
	    	
	    	//try to get the token from the manager
	    	Token token = TokenManager.instance()
	    			.retrieveTokenWithScope(user.getEmail(), AuthorisationScope.VIEW_PRIVATE);
        	
        	ITeamService teamService = new TeamService(em());
        	
        	Strava strava = new Strava(token);
        	for (User teamMember : team.getUsers()) {
        		StravaAthlete athlete = strava.getAthlete(teamMember.getStrava_id());
        		teamService.removeTeamMember(teamMember, teamid);
        	}
        	return redirect(routes.MyTeamController.details(teamid));
   }
    	
		
    	
   
	/**
	 * Render team detail view
	 * 
	 * @return http response
	 */
	@Transactional()
	public Result newTeam(){

		Form<Team> form = formFactory.form(Team.class);
		
		
		
		return ok(detail.render(form));
	}
    
	/**
	 * Render team detail view
	 * pass team to model
	 * 
	 * @param id Team id
	 * @return http response
	 */
	@Transactional(readOnly=true)
	public Result details(int id){

		//EntityManager em = jpa.em();
		//final Team team = em.find(Team.class, id);
		
		TeamDao dao = new TeamDao(jpa.em());
		final Team team = dao.findById(id);
		
		if (team == null) {
			return notFound(String.format("Team %s does not exist.", id));
		}
		Form<Team> form = formFactory.form(Team.class);
		return ok(detail.render(form.fill(team)));
	}


	@Transactional()
	public Result save(){
		Form<Team> teamForm = formFactory.form(Team.class).bindFromRequest();
		if(teamForm.hasErrors()){
			flash("error", "Please correct the form below.");
			return badRequest(detail.render(teamForm));
		}
		Team team  = teamForm.get();
		
		TeamDao dao = new TeamDao(jpa.em());
		dao.save(team);
		//EntityManager em = jpa.em();
		//em.merge(team);
		
		flash("success", String.format("Saved team %s", team));
		return redirect(routes.TeamController.index());
	}
	
	@Transactional()
	public Result delete(int id) {
		TeamDao dao = new TeamDao(jpa.em());
		Team team = dao.findById(id);
		
		if(team == null) {
			return notFound(String.format("Product %s does not exists.", id));
		}
		dao.delete(team);
		return redirect(routes.TeamController.index());

	}
    

}
