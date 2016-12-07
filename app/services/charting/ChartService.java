package services.charting;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javastrava.api.v3.model.StravaActivity;
import models.vm.Bar;
import models.vm.BarChart;

/**
 * Chart services.
 * 
 * @author TEAM RMG
 *
 */
public class ChartService implements IChartService {

	/**
	 * Prepare chart view model.
	 * 
	 * @param activities Strava activities from the last year
	 * @param now key date
	 * @return BarChart view model
	 */
	@Override
	public BarChart PrepareYearlyActivityChart(List<StravaActivity> activities, LocalDateTime now) {
    	
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
    	List<Bar> bars = new ArrayList<Bar>();
    	for (int i = 11; i >= 0; i--) {
    		Bar bar = new Bar();
    		LocalDateTime dday = now.minusMonths(i);
    		String period = dday.format(dtf);
    		bar.setPeriod(period);
    		
    		//all activities from target month
    		Stream<StravaActivity> monthlyActivities = activities.stream()
    				.filter(act->
    						act.getStartDateLocal().getYear() == dday.getYear() &&
    						act.getStartDateLocal().getMonth() == dday.getMonth());
    		
    		double meters = monthlyActivities.mapToDouble(ma->ma.getDistance()).sum();
    		bar.setKmByMeters(meters);
    		bars.add(bar);
		}
    	
    	BarChart vm = new BarChart("km", "Total Km");
    	vm.setBars(bars);
    	return vm;
	}

}
