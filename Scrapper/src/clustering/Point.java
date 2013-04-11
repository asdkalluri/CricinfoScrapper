package clustering;

import java.text.DecimalFormat;

public class Point {
	
	private String label;
	private double xVal;
	private double yVal;
	
	Point(String name, double x, double y)
	{
		this.setLabel(name);
		this.setxVal(x);
		this.setyVal(y);
	}
	
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public double getxVal() {
		return xVal;
	}
	public void setxVal(double xVal) {
		this.xVal = xVal;
	}
	public double getyVal() {
		return yVal;
	}
	public void setyVal(double yVal) {
		this.yVal = yVal;
	}
	
	public void printPointDetails()
	{
		DecimalFormat df = new DecimalFormat("0.00");
		System.out.println("["+this.getLabel()+","+df.format(this.getxVal())+","+df.format(this.getyVal())+"]");
		//System.out.println(df.format(this.getVal()));
	}
	
}
