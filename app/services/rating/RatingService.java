package services.rating;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaActivityType;
import models.Task;
import models.vm.UserViewModel;


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
		
	}
	
	/**
	 * Ratio ride:run 17:4 --> Ironman ratio
	 * 
	 * @param activities
	 * @return score
	 */
	public int calculateScore(List<StravaActivity> activities){
		
		int points = 0;
		int runMeters = 0;
		int rideMeters = 0;
		for (StravaActivity activity : activities) {
			if(activity.getType() == StravaActivityType.RUN){
				runMeters += activity.getDistance();	
			}else if (activity.getType() == StravaActivityType.RIDE){
				rideMeters += activity.getDistance();
			}
			
			//float speed = activity.getAverageSpeed();
			//for a future version we can include the average speed
			//to calculate a more accurate rating
		}
		points += Math.round((17 * runMeters/1000));
		points += Math.round((4  * rideMeters/1000));
		return points;
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
		
		int totalTodoMeters = tasks.stream().mapToInt(t->t.getMeters()).sum();
		
		//get timestamp of first task
		Date start = tasks.stream().map(u -> u.getTs()).min(Date::compareTo).get();
		Instant tsInstant = start.toInstant();
		
		//find relevant activities - we'll ignore older activities
		Stream<StravaActivity> activities = vm.getActivities().stream()
				.filter(act-> act.getStartDate().toInstant().isAfter(tsInstant) );
		
		double metersDone = activities.mapToDouble(ma->ma.getDistance()).sum();
		
		int completed = calculateCompleteness(totalTodoMeters, metersDone);
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
