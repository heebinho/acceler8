package controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.Strava;
import models.User;
import models.vm.BarChart;
import models.vm.UserViewModel;
import play.Logger;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import services.charting.ChartService;
import services.charting.IChartService;
import services.rating.IRatingService;
import services.rating.RatingService;
import services.user.IUserService;
import services.user.UserService;
import views.html.dashboard.*;


/**
 * Dashboard controller.
 * 
 * 
 * @author TEAM RMG
 *
 */
@Security.Authenticated(Secured.class)
public class DashboardController extends BaseController {
	

	/**
	 * Index action.
     * Default dashboard action. Build user view model.
     * 
     * @return Result render dashboard 
     */
    @Transactional
    public Result index() {
    	try {
        	String email = ctx().session().get("email");
        	
        	IUserService userService = new UserService(em());
        	User user = userService.findByEmail(email);
        	
        	Token token = userService.getStravaAccessToken(user);
        	if(token == null){
        		//we can't get data from strava without an access token. Request one.
        		return redirect(controllers.account.routes.SettingsController.index());
        	}
        	UserViewModel vm = new UserViewModel();
        	vm.setUser(user);
        	StravaAthlete athlete = token.getAthlete();
        	vm.setAthlete(athlete);
        	
        	LocalDateTime now = LocalDate.now().atStartOfDay();
        	Strava strava = new Strava(token);
        	List<StravaActivity> activities = strava.listAllAuthenticatedAthleteActivities(null, now.minusYears(1));
        	//order activities by start date
        	Stream<StravaActivity> orderedActivities = activities.stream()
        			.sorted((a1,a2) -> a2.getStartDateLocal().compareTo(a1.getStartDateLocal()));
        	vm.setActivities(orderedActivities.collect(Collectors.toList()));
    		
        	if(athlete.getProfileMedium().startsWith("http"))
    			vm.setProfileImage(athlete.getProfileMedium());
        	
        	IRatingService rating = new RatingService();
        	rating.rateUserScore(vm);
        	
        	return ok(index.render(vm));			
		} catch (Exception any) {
			Logger.error(any.getMessage());
			return redirect(routes.HomeController.index());
		}
    }
    
    /**
     * Chart action.
     * Build chart view model.
     * 
     * @return Result view model as Json.
     */
    @Transactional
    public Result chart() {
    	try {
        	String email = ctx().session().get("email");
        	IUserService userService = new UserService(em());
        	User user = userService.findByEmail(email);
        	Token token = userService.getStravaAccessToken(user);
        	
        	Strava strava = new Strava(token);
        	LocalDateTime now = LocalDate.now().atStartOfDay();
        	//get all activities of the last 12 months
        	List<StravaActivity> activities = strava.listAllAuthenticatedAthleteActivities(null, now.minusYears(1));
        	IChartService chartService = new ChartService();
        	BarChart chart = chartService.PrepareYearlyActivityChart(activities, now);

        	//return chart as json object
    		return ok(Json.toJson(chart));
		} catch (Exception any) {
			Logger.error("Not able to load chart view model." + any.getMessage());
			flash("error", "Chart not loaded");
			return internalServerError();
		}
    }

}
