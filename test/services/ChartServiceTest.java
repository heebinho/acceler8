package services;
import org.junit.*;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaActivityType;
import models.vm.Bar;
import models.vm.BarChart;
import services.charting.ChartService;
import services.charting.IChartService;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Test chart services
 * 
 * 
 * @author TEAM RMG
 *
 */
public class ChartServiceTest {

	
	/**
	 * Test our chart service
	 * Mock some activities and generate chart
	 */
    @Test
    public void checkGeneratedChart() {
    	double kmsInJuly = 5.1; //Total for july
    	double kmInAugust = 13; //Total for august
    	
    	IChartService service = new ChartService();
    	LocalDateTime now = LocalDateTime.parse("2016-10-04T10:05:30");
    	BarChart chart = service.PrepareYearlyActivityChart(mockActivities(), now);
    	
    	//test bar from august
		Optional<Bar> bar =  chart.getBars().stream()
				.filter(b -> b.getPeriod().equals("2016-08")).findFirst() ;
		
		assertTrue(bar.isPresent());
		if(bar.isPresent())
			assertTrue(kmInAugust == bar.get().getKm());
		
    	//test bar from Juli
		bar =  chart.getBars().stream()
				.filter(b -> b.getPeriod().equals("2016-07")).findFirst() ;
		
		assertTrue(bar.isPresent());
		if(bar.isPresent())
			assertTrue(kmsInJuly== bar.get().getKm());
		
    	//assert that bar from oktober 2015 is not present
		bar =  chart.getBars().stream()
				.filter(b -> b.getPeriod().equals("2015-10")).findFirst() ;
		assertTrue(!bar.isPresent());
		
    	//assert that bar from november 2015 is present
		bar =  chart.getBars().stream()
				.filter(b -> b.getPeriod().equals("2015-11")).findFirst() ;
		assertTrue(bar.isPresent());
		if(bar.isPresent())
			assertTrue(0== bar.get().getKm());
		
		//assert overall size
		assertTrue(chart.getBars().size() == 12);
    }
    
    private List<StravaActivity> mockActivities(){
    	List<StravaActivity> activities = new ArrayList<StravaActivity>();
    	activities.add(newActivity("2016-07-31T10:05:30", 5100, 4f, StravaActivityType.RUN));
    	activities.add(newActivity("2016-08-01T10:05:30", 8100, 4f, StravaActivityType.RUN));
    	activities.add(newActivity("2016-08-04T10:05:30", 1900, 4f, StravaActivityType.RUN));
    	activities.add(newActivity("2016-08-31T10:05:30", 3000, 5f, StravaActivityType.RUN));
    	activities.add(newActivity("2016-09-01T00:00:00", 3000, 5f, StravaActivityType.RUN));
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
