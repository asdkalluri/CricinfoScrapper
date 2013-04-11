package clustering;

import java.text.DecimalFormat;

public class Distance {

	Point one;
	Point two;
	double distanceValue;

	Distance(Point p1, Point p2, double d)
	{
		this.setOne(p1);
		this.setTwo(p2);
		this.setDistanceValue(d);
	}

	public Point getOne() {
		return one;
	}
	public void setOne(Point one) {
		this.one = one;
	}
	public Point getTwo() {
		return two;
	}
	public void setTwo(Point two) {
		this.two = two;
	}
	public double getDistanceValue() {
		return distanceValue;
	}
	public void setDistanceValue(double distanceValue) {
		this.distanceValue = distanceValue;
	}

	public void printDistanceDetails()
	{
		this.getOne().printPointDetails();
		this.getTwo().printPointDetails();
		DecimalFormat df = new DecimalFormat("0.0000");
		System.out.println(df.format(this.getDistanceValue()));
		
	}
}
