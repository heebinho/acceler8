package controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.JsonNode;

import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.Strava;
import models.User;
import models.vm.Achievement;
import models.vm.BarChart;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import services.user.IUserService;
import services.user.UserService;
import views.html.dashboard.*;


/**
 * Dashboard.
 * 
 * @author TEAM RMG
 *
 */
@Security.Authenticated(Secured.class)
public class DashboardController extends BaseController {
	


	/**
     * Default action dashboard.
     */
    @Transactional
    public Result index() {
    	
    	String email = ctx().session().get("email");
    	
    	IUserService userService = new UserService(em());
    	User user = userService.findByEmail(email);
    	
    	Token token = userService.getStravaAccessToken(user);
    	if(token == null){
    		//we can't get data from strava without an access token. Request one.
    		return redirect(controllers.account.routes.SettingsController.index());
    	}
    	
    	StravaAthlete athlete = token.getAthlete();
    	Strava strava = new Strava(token);
    	LocalDateTime now = LocalDate.now().atStartOfDay();
    	//List<StravaActivity> activities = strava.listAllAuthenticatedAthleteActivities();
    	List<StravaActivity> activities = strava.listAllAuthenticatedAthleteActivities(null, now.minusYears(1));
    	
    	
    	JsonNode resultJson = getMonthlyAchievements(activities, now);
    	
    	return ok(index.render(athlete, activities , resultJson));
    }
    
    @Transactional
    public Result chart() {
    	String email = ctx().session().get("email");
    	IUserService userService = new UserService(em());
    	User user = userService.findByEmail(email);
    	Token token = userService.getStravaAccessToken(user);
    	
    	Strava strava = new Strava(token);
    	LocalDateTime now = LocalDate.now().atStartOfDay();
    	//get all activities of the last 12 months
    	List<StravaActivity> activities = strava.listAllAuthenticatedAthleteActivities(null, now.minusYears(1));
    	JsonNode resultJson = getMonthlyAchievements(activities, now);

		return ok(resultJson);
    }
    
    private JsonNode getMonthlyAchievements(List<StravaActivity> activities, LocalDateTime now){
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
    	List<Achievement> data = new ArrayList<Achievement>();
    	for (int i = 11; i >= 0; i--) {
    		Achievement achievement = new Achievement();
    		LocalDateTime month = now.minusMonths(i);
    		String period = month.format(dtf);
    		achievement.setPeriod(period);
    		
    		Stream<StravaActivity> monthlyActivities = activities.stream()
    				.filter(act->
    						act.getStartDateLocal().getYear() == month.getYear() &&
    						act.getStartDateLocal().getMonth() == month.getMonth());
    		
    		double meters = monthlyActivities.mapToDouble(ma->ma.getDistance()).sum();
    		achievement.setMeters(meters);
    		data.add(achievement);
		}
    	
    	
    	BarChart vm = new BarChart("meters", "meters");
    	//data.add(new Achievement("2016-06", 10000));
    	vm.setData(data);
		return Json.toJson(vm);
    }

}
