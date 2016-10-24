package services.charting;

import java.time.LocalDateTime;
import java.util.List;

import javastrava.api.v3.model.StravaActivity;
import models.vm.BarChart;

/**
 * Chart services interface.
 * 
 * @author TEAM RMG
 *
 */
public interface IChartService {
	
	/**
	 * Prepare chart view model.
	 * 
	 * @param activities Strava activities from the last year
	 * @param now key date
	 * @return BarChart view model
	 */
	BarChart PrepareYearlyActivityChart(List<StravaActivity> activities, LocalDateTime now);

}
