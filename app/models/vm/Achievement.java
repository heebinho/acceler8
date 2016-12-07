package models.vm;

/**
 * Achievement. Used as chart series.
 * 
 * @author TEAM RMG
 *
 */
public class Achievement {
	
	public Achievement(){}
	
	public Achievement(String period, double meters){
		this.period = period;
		this.meters = meters;
	}

	private String period;
	
	private double meters;

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public double getMeters() {
		return meters;
	}

	public void setMeters(double meters) {
		this.meters = meters;
	}
	
	
}
