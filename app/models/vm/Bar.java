package models.vm;

/**
 * Chart bar. Used as chart series.
 * 
 * @author TEAM RMG
 *
 */
public class Bar {
	
	public Bar(){}
	
	public Bar(String period, double meters){
		this.period = period;
		this.km = meters;
	}

	private String period;
	
	private double km;

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public double getKm() {
		return km;
	}

	public void setKm(double km) {
		//this.km = Math.round(km);
		this.km = Math.round(km * 100.0) / 100.0;
	}
	
	public void setKmByMeters(double meters) {
		setKm(meters/1000);
	}
	
	
}
