package controllers;

import play.mvc.*;
import services.account.AccountService;
import services.account.IAccountService;
import services.team.ITeamService;
import services.team.TeamService;
import services.user.IUserService;
import services.user.UserService;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.libs.mailer.MailerClient;

import java.util.List;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import javastrava.api.v3.auth.TokenManager;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.auth.ref.AuthorisationScope;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.Strava;
import models.Mail;
import models.Team;
import models.User;
import models.dao.TeamDao;
import models.vm.InviteResult;
import models.vm.Invite;
import models.vm.TeamViewModel;
import models.vm.UserViewModel;
import views.html.team.show;
import views.html.team.*;




/**
 * Controls team requests
 * 
 * @author Team RMG
 */
@Security.Authenticated(Secured.class)
public class TeamController extends BaseController {
	
	@Inject 
	FormFactory formFactory;
	
	@Inject
    MailerClient mailerClient;

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
    	IUserService userService = new UserService(em());
    	User user = userService.findByEmail(email);
    	
    	ITeamService teamService = new TeamService(em());
    	teamService.removeMember(user, teamId);
    	
		return redirect(routes.TeamController.index());
    }
	
	@Transactional()
    public Result join(int id) {
		
    	String email = ctx().session().get("email");
    	IUserService userService = new UserService(em());
    	User user = userService.findByEmail(email);
    	
    	ITeamService teamService = new TeamService(em());
    	if(teamService.isAlreadyMember(user, id)){
    		return redirect(routes.TeamController.show(id));
    	}
    	teamService.addNewMember(user, id);
    	return redirect(routes.TeamController.show(id));
    }
	
	@Transactional()
    public Result invite() {
		
		InviteResult result = new InviteResult();
		Form<Invite> inviteForm = formFactory.form(Invite.class).bindFromRequest();
		if(inviteForm.hasErrors()){
			return badRequest();
		}
		Invite invite = inviteForm.get();
		
		try{
	        String subject = getMessage("mail.invite.subject");
	        String message = getMessage("mail.invite.message") + "\n\n";
	        message += routes.TeamController.join(invite.getTeamId()).absoluteURL(request());
	        
	        Mail.Envelop envelop = new Mail.Envelop(subject, message, invite.getEmail() );
	        Mail mail = new Mail(mailerClient);
	        mail.sendMail(envelop);
	        result.setSent(true);
		}catch(Exception any){
			Logger.error(any.getMessage());
		}
		
		//serialize and serve as json
		JsonNode resultJson = Json.toJson(result);
		return ok(resultJson);
	}
	
	/**
	 * Remove a user from a team
	 * 
	 * @param id team id
	 * @param uid Strava athlete id
	 * @return
	 */
	@Transactional()
    public Result removeUser(int id, int uid) {
    	
    	IUserService userService =new UserService(em());
    	User user = userService.findByAthleteId(uid);
    	
    	ITeamService service = new TeamService(em());
    	Team team = service.findById(id);
    	team.getUsers().remove(user);

    	return redirect(routes.TeamController.show(id));
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
	
    @Transactional
    public Result my() {
    	
    	String email = ctx().session().get("email");
    	IUserService userService = new UserService(em());
    	User user = userService.findByEmail(email);
    	
    	ITeamService teamService = new TeamService(em());
    	List<Team> teams = teamService.getTeamsByUser(user.getId());
    	
    	return ok(my.render(teams));
    }
	
    @Transactional
    public Result show(int id) {
    	
    	String email = ctx().session().get("email");
    	
    	IUserService userService = new UserService(em());
    	User user = userService.findByEmail(email);
    	
    	TeamViewModel vm = new TeamViewModel();
    	ITeamService service = new TeamService(em());
    	vm.setTeam(service.findById(id));
    	
    	Token token = TokenManager.instance()
    			.retrieveTokenWithScope(user.getEmail(), AuthorisationScope.VIEW_PRIVATE);
    	
    	Strava stravaService = new Strava(token);
    	for (User teamMember : vm.getTeam().getUsers()) {
    		StravaAthlete athlete = stravaService.getAthlete(teamMember.getStrava_id());
    		
    		UserViewModel uVm = new UserViewModel();
    		vm.addMember(uVm);
    		uVm.setAthlete(athlete);
    		uVm.setUser(teamMember);
    		
    		if(athlete.getProfileMedium().startsWith("http"))
    			uVm.setProfileImage(athlete.getProfileMedium());
    		
    		if(user.getId() == teamMember.getId())
    			vm.setMember(true);
		}
    	
    	Form<Invite> inviteForm = formFactory.form(Invite.class);
    	return ok(show.render(vm, inviteForm));
    }
    

}
