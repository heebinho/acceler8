package controllers;

import play.mvc.*;
import services.rating.IRatingService;
import services.rating.RatingService;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import javastrava.api.v3.auth.TokenManager;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.auth.ref.AuthorisationScope;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.Strava;
import models.Mail;
import models.Task;
import models.Team;
import models.User;
import models.dao.TeamDao;
import models.vm.InviteResult;
import models.vm.TaskViewModel;
import models.vm.Invite;
import models.vm.TeamViewModel;
import models.vm.UserViewModel;
import views.html.team.*;




/**
 * Team controller.
 * Handles team requests
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
     * Index action.
     * List all teams.
     * 
     * @return Result render list view
     */
	@Transactional()
    public Result index() {
    	ITeamService teamService = new TeamService(em());
    	List<Team> teams = teamService.findAllTeams();
    	
    	return ok(list.render(teams));
    }

	/**
	 * Leave action.
	 * 
	 * @param teamId team to leave
	 * @return Result redirect to team list (index)
	 */
	@Transactional()
    public Result leave(int teamId) { 
		String email = ctx().session().get("email");
    	IUserService userService = new UserService(em());
    	User user = userService.findByEmail(email);
    	
    	ITeamService teamService = new TeamService(em());
    	teamService.removeMember(user, teamId);
    	
		return redirect(routes.TeamController.index());
    }
	
	/**
	 * Join action.
	 * 
	 * @param teamId id of the team
	 * @return Result redirect show team
	 */
	@Transactional()
    public Result join(int teamId) {
		
    	String email = ctx().session().get("email");
    	IUserService userService = new UserService(em());
    	User user = userService.findByEmail(email);
    	
    	ITeamService teamService = new TeamService(em());
    	if(teamService.isAlreadyMember(user, teamId)){
    		return redirect(routes.TeamController.show(teamId));
    	}
    	teamService.addNewMember(user, teamId);
    	return redirect(routes.TeamController.show(teamId));
    }
	
	/**
	 * Invite action.
	 * Sends an invitation to the given mail address.
	 * 
	 * @return Result json
	 */
	@Transactional()
    public Result invite() {
		
		InviteResult result = new InviteResult();
		Form<Invite> inviteForm = formFactory.form(Invite.class).bindFromRequest();
		if(inviteForm.hasErrors()){
			return badRequest();
		}
		
		try{
			Invite invite = inviteForm.get();
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
		return ok(Json.toJson(result));
	}
	
	/**
	 * Removeuser action.
	 * Remove a user from a team
	 * 
	 * @param id team id
	 * @param uid Strava athlete id
	 * @return Result redirect team show
	 */
	@Transactional()
    public Result removeUser(int id, int uid) {
    	
    	IUserService userService = new UserService(em());
    	User user = userService.findByAthleteId(uid);
    	
    	ITeamService service = new TeamService(em());
    	Team team = service.findById(id);
    	team.getUsers().remove(user);

    	return redirect(routes.TeamController.show(id));
   }
		
	/**
	 * NewTeam action.
	 * Render team detail view.
	 * 
	 * @return Result detail view
	 */
	public Result newTeam(){

		Form<Team> form = formFactory.form(Team.class);
		return ok(detail.render(form));
	}
    
	/**
	 * Details action.
	 * Render team detail view
	 * pass team to model
	 * 
	 * @param id Team id
	 * @return Result detail view
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


	/**
	 * Save action. (POST)
	 * Save a team-
	 * 
	 * @return Result redirect and flash message
	 */
	@Transactional()
	public Result save(){
		Form<Team> teamForm = formFactory.form(Team.class).bindFromRequest();
		if(teamForm.hasErrors()){
			flash("error", getMessage("form.haserrors"));
			return badRequest(detail.render(teamForm));
		}
		try {
	    	String email = ctx().session().get("email");
	    	IUserService userService = new UserService(em());
	    	User user = userService.findByEmail(email);
			
	    	ITeamService teamService = new TeamService(em());
	    	Team team  = teamForm.get();
	    	if(!teamService.isAvailable(team.getName())){
	    		flash("error", getMessage("team.name.not.available"));
				return badRequest(detail.render(teamForm));
	    	}
	    	
			team.setCoach(user);
			team.getUsers().add(user);
			
			
			Team savedTeam = teamService.persistTeam(team);
			
			flash("success", getMessage("team.saved", team));
			return redirect(routes.TeamController.show(savedTeam.getId()));
			
		} catch (Exception any) {
			Logger.error(any.getMessage());
			flash("error", getMessage("error.technical"));
			return redirect(routes.HomeController.index());
		}
	}
	
	/**
	 * Delete action.
	 * Deletea a team.
	 * 
	 * @param id Team id
	 * @return Result http 200 or redirect
	 */
	@Transactional()
	public Result delete(int id) {
		try {
			ITeamService teamService = new TeamService(em());
			Team team = teamService.findById(id);
			teamService.deleteTeam(team);

			return ok();			
		} catch (Exception any) {
			Logger.error(any.getMessage());
			return redirect(routes.TeamController.index());
		}
	}
	
	/**
	 * My action.
	 * Shows my teams.
	 * 
	 * @return Result my teams view
	 */
    @Transactional
    public Result my() {
    	String email = ctx().session().get("email");
    	IUserService userService = new UserService(em());
    	User user = userService.findByEmail(email);
    	
    	ITeamService teamService = new TeamService(em());
    	List<Team> teams = teamService.getTeamsByUser(user.getId());
    	
    	return ok(my.render(teams));
    }
    
	/**
	 * Coached action.
	 * Shows coached teams.
	 * 
	 * @return Result coached teams view
	 */
    @Transactional
    public Result coached() {
    	String email = ctx().session().get("email");
    	IUserService userService = new UserService(em());
    	User user = userService.findByEmail(email);
    	
    	ITeamService teamService = new TeamService(em());
    	List<Team> teams = teamService.getCoachedTeamsByUser(user);
    	
    	return ok(coached.render(teams));
    }
	
    /**
     * Show action.
     * Build and server team view model.
     * 
     * @param id team to show
     * @return Result http 200 ok & rendered view or redirect
     */
    @Transactional
    public Result show(int id) {
    	try {
    		TeamViewModel vm = new TeamViewModel();
    		
    		String email = ctx().session().get("email");
	    	IUserService userService = new UserService(em());
	    	User user = userService.findByEmail(email);
	    	vm.setUser(user);
	    	
	    	ITeamService service = new TeamService(em());
	    	Team team = service.findById(id); 
	    	vm.setTeam(team);
	    	
	    	Token token = TokenManager.instance()
	    			.retrieveTokenWithScope(user.getEmail(), AuthorisationScope.VIEW_PRIVATE);
	    	
	    	Strava stravaService = new Strava(token);
	    	StravaAthlete authenticatedAthlete = stravaService.getAuthenticatedAthlete();
	    	//to get the ratings of group peers they need to be friends.
	    	List<StravaActivity> friendActivities = stravaService.listAllFriendsActivities();
	    	List<Task> tasks = service.findAllTasks(team);
	    	IRatingService rate = new RatingService();
	    	for (User teamMember : vm.getTeam().getUsers()) {
	    		UserViewModel uVm = new UserViewModel();
	    		StravaAthlete athlete = stravaService.getAthlete(teamMember.getStrava_id());
	    		vm.addMember(uVm);
	    		uVm.setAthlete(athlete);
	    		uVm.setUser(teamMember);
	    		
	    		if(teamMember.getStrava_id().equals(authenticatedAthlete.getId())){
	    			uVm.setActivities(stravaService.listAllAuthenticatedAthleteActivities());
	    		}else {
	        		Stream<StravaActivity> activities = friendActivities.stream()
	        				.filter(act->act.getAthlete().getId().equals(athlete.getId()) );
	        		uVm.setActivities(activities.collect(Collectors.toList()));    			
	    		}
	    		rate.rateUserScore(uVm);
	    		rate.rateUserTasksScore(tasks, uVm);
	    		
	    		if(athlete.getProfileMedium().startsWith("http"))
	    			uVm.setProfileImage(athlete.getProfileMedium());
	    		
	    		if(user.getId() == teamMember.getId())
	    			vm.setMember(true);
			}
	    	
	    	Form<Invite> inviteForm = formFactory.form(Invite.class);
	    	return ok(show.render(vm, inviteForm));
    	
    	} catch (Exception any) {
			Logger.error(any.getMessage());
			flash("error", getMessage("error.technical" ));
			return redirect(routes.HomeController.index());
		}
    }
    
    /**
     * Tasks action.
     * Read and serve tasks of team.
     * 
     * @param id team
     * @return Result http 200 ok & rendered view or redirect
     */
    @Transactional
    public Result tasks(int id) {
    	try {
    		TaskViewModel vm = new TaskViewModel();
    		
    		String email = ctx().session().get("email");
	    	IUserService userService = new UserService(em());
	    	User user = userService.findByEmail(email);
	    	vm.setUser(user);
	    	
	    	ITeamService service = new TeamService(em());
	    	Team team = service.findById(id); 
	    	vm.setTeam(team);
	    	vm.setTasks(service.findAllTasks(team));
    		vm.setMember(team.getUsers().contains(user));
	    	
	    	Form<Task> taskForm = formFactory.form(Task.class);
	    	return ok(tasks.render(vm, taskForm));
    	
    	} catch (Exception any) {
			Logger.error(any.getMessage());
			flash("error", "cannot load team tasks: " + id );
			return redirect(routes.HomeController.index());
		}
    }
    
	/**
	 * Save task - POST
	 * Save a team task.
	 * 
	 * @return Result redirect and flash message
	 */
	@Transactional()
	public Result savetask(int id){
		Form<Task> taskForm = formFactory.form(Task.class).bindFromRequest();
		if(taskForm.hasErrors()){
			flash("error", getMessage("team.task.haserrors"));
			return tasks(id);
		}
		try {
	    	String email = ctx().session().get("email");
	    	IUserService userService = new UserService(em());
	    	User user = userService.findByEmail(email);
	    	
			ITeamService teamService = new TeamService(em());
			Team team = teamService.findById(id);
	    	
	    	if(user.getId() != team.getCoach().getId()){
	    		flash("error", getMessage("team.task.onlycoach"));
	    		return redirect(routes.TeamController.tasks(id));
	    	}
			
	    	Task task  = taskForm.get();
			teamService.addNewTask(task, team);
			
			flash("success", getMessage("team.task.saved"));
			return redirect(routes.TeamController.tasks(id));
			
		} catch (Exception any) {
			Logger.error(any.getMessage());
			flash("error", "not able to save task");
			return redirect(routes.TeamController.tasks(id));
		}
	}
	
	/**
	 * Deletetask action.
	 * Delete a task from a team.
	 * 
	 * @param id task to delete
	 * @return Result ok or redirect
	 */
	@Transactional()
	public Result deletetask(int id) {
		try {
			ITeamService teamService = new TeamService(em());
			teamService.removeTask(id);
			return ok();			
		} catch (Exception any) {
			Logger.error(any.getMessage());
			return redirect(routes.TeamController.index());
		}
	}
    
}
