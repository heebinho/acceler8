package services;
import org.junit.*;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaActivityType;
import models.vm.UserViewModel;
import services.rating.*;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Test rating services
 * 
 * 
 * @author TEAM RMG
 *
 */
public class RatingTest {

	
	/**
	 * Test our user score algorithm.
	 * Mock some activities and calculate
	 */
    @Test
    public void checkUserScoreCalculationWithOneActivity() {
    	int points = 26; //26.4
    	
    	IRatingService service = new RatingService();
    	UserViewModel vm = new UserViewModel();
    	vm.setActivities(mockOneRunActivity());
    	service.rateUserScore(vm);
    	
        assertTrue(points == vm.getTotalPoints());
    }
    
    /**
	 * Test our user score algorithm.
	 * Mock some activities and calculate
	 */
    @Test
    public void checkUserScoreCalculationWithTwoActivities() {
    	int points = 63; //26.4 + 45.36 = 63.36
    	
    	IRatingService service = new RatingService();
    	UserViewModel vm = new UserViewModel();
    	vm.setActivities(mockTwoRunActivities());
    	service.rateUserScore(vm);
    	
        assertTrue(points == vm.getTotalPoints());
    }
    
    
    private List<StravaActivity> mockOneRunActivity(){
    	List<StravaActivity> activities = new ArrayList<StravaActivity>();
    	activities.add(newActivity("2016-08-04T10:05:30", 5500, 3f, StravaActivityType.RUN));
    	return activities;
    }
    
    private List<StravaActivity> mockTwoRunActivities(){
    	List<StravaActivity> activities = new ArrayList<StravaActivity>();
    	activities.add(newActivity("2016-08-04T10:05:30", 8100, 4f, StravaActivityType.RUN));
    	activities.add(newActivity("2016-10-04T10:05:30", 3000, 5f, StravaActivityType.RUN));
    	return activities;
    }
    
    private StravaActivity newActivity(String dateTime, float distance, float speed, StravaActivityType type){
    	StravaActivity activity = new StravaActivity();
    	String strDatewithTime = dateTime;
    	LocalDateTime start = LocalDateTime.parse(strDatewithTime);
    	activity.setStartDateLocal(start);
    	activity.setDistance(distance);
    	activity.setAverageSpeed(speed);
    	activity.setType(type);
    	return activity;
    }


}
