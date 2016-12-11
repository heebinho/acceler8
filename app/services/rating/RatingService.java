package services.rating;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaActivityType;
import models.vm.UserViewModel;
import play.Logger;


/**
 * Rating service implementation.
 * 
 * @author TEAM RMG
 *
 */
public class RatingService implements IRatingService {

    /**
     * Rate user
     *
     * @param vm User to rate
     */
	@Override
	public void rateUser(UserViewModel vm) {
		
		//All activities
		vm.setTotalPoints(getScore(vm.getActivities()));
		
		LocalDateTime now = LocalDate.now().atStartOfDay();
		//Monthly activities
		Stream<StravaActivity> monthlyActivities = vm.getActivities().stream()
				.filter(act->
						act.getStartDateLocal().getYear() == now.getYear() &&
						act.getStartDateLocal().getMonth() == now.getMonth());
		vm.setMonthlyPoints(getScore(monthlyActivities.collect(Collectors.toList())));
		//Weekly activities
		LocalDateTime weekStart = now.with(DayOfWeek.MONDAY);
		Stream<StravaActivity> weeklyActivities = vm.getActivities().stream()
				.filter(act->
						act.getStartDateLocal().getYear() == now.getYear() &&
						act.getStartDateLocal().getMonth() == now.getMonth() && 
						act.getStartDateLocal().getDayOfYear() >= weekStart.getDayOfYear());
		vm.setWeeklyPoints(getScore(weeklyActivities.collect(Collectors.toList())));
		
	}
	
	/**
	 * Ratio ride:run 17:4 --> Ironman ratio
	 * 
	 * @param activities
	 * @return
	 */
	private int getScore(List<StravaActivity> activities){
		
		int points = 0;
		int runMeters = 0;
		int rideMeters = 0;
		for (StravaActivity activity : activities) {
			if(activity.getType() == StravaActivityType.RUN){
				runMeters += activity.getDistance();	
			}else if (activity.getType() == StravaActivityType.RIDE){
				rideMeters += activity.getDistance();
			}
			
			float speed = activity.getAverageSpeed();
			Logger.info((speed)+"");
		}
		points += Math.round((17 * runMeters/1000));
		points += Math.round((4  * rideMeters/1000));
		return points;
	}

}
