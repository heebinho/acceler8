package models.vm;

import java.util.ArrayList;
import java.util.List;

/**
 * Bar chart. Json representation for morris.
 * Initialized with default values and a ctor for convenience.
 * 
 * @author TEAM RMG
 *
 */
public class BarChart {
	

	public BarChart(String ykey, String label){
		ykeys.add(ykey);
		labels.add(label);
	}
	
	private String element = "activities-bar-chart";
	
	private List<Bar> bars = new ArrayList<Bar>();
	
	private String xkey;
	
	private List<String> ykeys = new ArrayList<String>();
	
	private List<String> labels = new ArrayList<String>();
	
	private int pointSize = 2;
	
	private String hideHover = "auto";
	
	private boolean resize = true;
	
	private String xLabels = "month"; 

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public List<Bar> getBars() {
		return bars;
	}

	public void setBars(List<Bar> data) {
		this.bars = data;
	}

	public String getXkey() {
		return xkey;
	}

	public void setXkey(String xkey) {
		this.xkey = xkey;
	}

	public List<String> getYkeys() {
		return ykeys;
	}

	public void setYkeys(List<String> ykeys) {
		this.ykeys = ykeys;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public int getPointSize() {
		return pointSize;
	}

	public void setPointSize(int pointSize) {
		this.pointSize = pointSize;
	}

	public String getHideHover() {
		return hideHover;
	}

	public void setHideHover(String hideHover) {
		this.hideHover = hideHover;
	}

	public boolean isResize() {
		return resize;
	}

	public void setResize(boolean resize) {
		this.resize = resize;
	}

	public String getxLabels() {
		return xLabels;
	}

	public void setxLabels(String xLabels) {
		this.xLabels = xLabels;
	}
	
}
