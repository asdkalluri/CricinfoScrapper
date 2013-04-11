package clustering;

import java.util.ArrayList;

public class Cluster {

	String label;
	Point center;
	ArrayList<Point> points;
	
	Cluster()
	{
		init();
	}
	
	
	public Point getCenter() {
		return center;
	}


	public void setCenter(Point center) {
		this.center = center;
	}


	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}

	public void addPoint(Point p)
	{
		points.add(p);
	}
	
	private void init()
	{
		points = new ArrayList<Point>();
	}
	
	public ArrayList<Point> getPoints()
	{
		return points;
	}
	
	public void printCluster()
	{
		System.out.println("Total Points Found:"+points.size());
		System.out.println("label:"+this.getLabel());
		//for(Point point: points)
			//point.printPointDetails();
	}
}
