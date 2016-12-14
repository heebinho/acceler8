package services.rating;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaActivityType;
import models.Task;
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
	public void rateUserScore(UserViewModel vm) {
		try {
			//All activities
			vm.setTotalPoints(calculateScore(vm.getActivities()));
			
			LocalDateTime now = LocalDate.now().atStartOfDay();
			//Monthly activities
			Stream<StravaActivity> monthlyActivities = vm.getActivities().stream()
					.filter(act->
							act.getStartDateLocal().getYear() == now.getYear() &&
							act.getStartDateLocal().getMonth() == now.getMonth());
			vm.setMonthlyPoints(calculateScore(monthlyActivities.collect(Collectors.toList())));
			//Weekly activities
			LocalDateTime weekStart = now.with(DayOfWeek.MONDAY);
			Stream<StravaActivity> weeklyActivities = vm.getActivities().stream()
					.filter(act->
							act.getStartDateLocal().getYear() == now.getYear() &&
							act.getStartDateLocal().getMonth() == now.getMonth() && 
							act.getStartDateLocal().getDayOfYear() >= weekStart.getDayOfYear());
			vm.setWeeklyPoints(calculateScore(weeklyActivities.collect(Collectors.toList())));
			
		} catch (Exception any) {
			Logger.error(any.getMessage());
		}

	}
	
	/**
	 * Ironman Ratio --> ride:run 17:4 
	 * We use		 --> ride:run 16:4 --> 4:1
	 * 
	 * @param activities
	 * @return score
	 */
	public int calculateScore(List<StravaActivity> activities){
		
		int points = 0;
		float runMeters = 0;
		float rideMeters = 0;
		float averageSpeed = 0;
		float performanceRewardFactor;
		
		for (StravaActivity activity : activities) {
			
			if(activity.getAverageSpeed() != null)
				averageSpeed = activity.getAverageSpeed();
			if(activity.getType() == StravaActivityType.RUN){
				runMeters = activity.getDistance();
				performanceRewardFactor = getRunPerformanceRewardFactor(averageSpeed);
				points += Math.round((4 * runMeters/1000) * performanceRewardFactor);
			}else if (activity.getType() == StravaActivityType.RIDE){
				rideMeters = activity.getDistance();
				performanceRewardFactor = getRidePerformanceRewardFactor(averageSpeed);
				points += Math.round((rideMeters/1000) * performanceRewardFactor);
			}
		}
		
		return points;
	}
	
	public float getRunPerformanceRewardFactor(float speed){

		if (isBetween(speed, 0f, 2.81f))
			 return 1.1f;
		else if (isBetween(speed, 2.81f, 3.17f))
			return 1.2f;
		else if (isBetween(speed, 3.17f, 3.62f))
			return 1.3f;
		else if (isBetween(speed, 3.62f, 4.22f))
			return 1.4f;
		else if (isBetween(speed, 4.22f, 5.06f))
			return 1.5f;
		else if (isBetween(speed, 5.06f, 6.33f))
			return 1.6f;
		else
			return 1.7f; //probably not a human being :)
	}
	
	public float getRidePerformanceRewardFactor(float speed){
		
		//ride performance factors will be added in a future version
		//we can also make these settings editable for the user.
		return 1f;
	} 
	
	private static boolean isBetween(float x, float lower, float upper){
		return lower <= x && x<= upper;
	}

	/**
	 * Calculate percentage of completed tasks
	 * 
	 * 
	 * @param tasks list of tasks
	 * @param vm User view model
	 */
	@Override
	public void rateUserTasksScore(List<Task> tasks, UserViewModel vm) {
		
		int completed = 100; //no tasks at all -> so everything completed at the moment 100%
		
		if(tasks.size() > 0){
			try {
				int totalTodoMeters = tasks.stream().mapToInt(t->t.getMeters()).sum();
				
				//get timestamp of first task
				Optional<Date> start = tasks.stream().map(u -> u.getTs()).min(Date::compareTo);
				if(start.isPresent()){
					Instant tsInstant = start.get().toInstant();
					
					//find relevant activities - we'll ignore older activities
					Stream<StravaActivity> activities = vm.getActivities().stream()
							.filter(act-> act.getStartDate().toInstant().isAfter(tsInstant) );
					
					double metersDone = activities.mapToDouble(ma->ma.getDistance()).sum();
					completed = calculateCompleteness(totalTodoMeters, metersDone);
				}	
			} catch (Exception any) {
				Logger.error("not able to calculate percentage of completed tasks", any.getMessage());
				completed = 0;
			}
		}

		vm.setCompleted(completed);
	}
	
	/**
	 * Calculate the completeness in percentage
	 * min 0; max 100
	 * 
	 * @param totalTodoMeters meters to absolve
	 * @param metersDone meters absolved
	 * @return int completeness
	 */
	public int calculateCompleteness(int totalTodoMeters, double metersDone){
		//if there's nothing to do, we'll return completed -> 100%
		//as a side effect we don't need to handle divison by zero
		if(totalTodoMeters == 0)
			return 100;
		
		int completed = (int)(100d / totalTodoMeters * metersDone);
		if(completed > 100) completed = 100;
		return completed;
	} 
	
	


}
