package clustering;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

import util.Utility;

public class HCluster {

	ArrayList<String> lines = new ArrayList<String>();
	ArrayList<Cluster> clusters = new ArrayList<Cluster>();
	public static int  MAX =2;
	public static int  MIN =1;
	public static int  AVG =3;


	public HCluster()
	{
		init();
	}

	public void init()
	{
		lines = Utility.readFile("files/C2.txt");
	}

	public void parseLinesIntoPoints()
	{
		int size = lines.size();
		for(int i=0;i<size;i++)
		{
			String line = lines.get(i);
			line = Utility.returnFirstNWords(line, 3);
			String tokens [] = line.split(Utility.space);
			String name = tokens[0];
			float x = Float.parseFloat(tokens[1]);
			float y = Float.parseFloat(tokens[2]);

			Cluster c = new Cluster();
			Point p = new Point(name, x, y);
			c.setLabel(p.getLabel());
			c.addPoint(p);
			clusters.add(c);
		}
	}

	/*public void printClusters(ArrayList<Cluster> clusterList)
	{
		for(Cluster c: clusterList)
			c.printCluster();
	}*/

	public double getDistance(Point p1, Point p2)
	{
		double squaredDistance = (Math.pow(p1.getxVal()- p2.getxVal(), 2) + Math.pow(p1.getyVal()- p2.getyVal(), 2));
		return Math.pow(squaredDistance, 0.5); 
	}

	public Distance getMinDistance(ArrayList<Cluster> clusterList)
	{
		TreeMap <Double, Distance> distanceMap = new TreeMap<Double, Distance>();

		int clusterSize = clusterList.size();
		for(int ii=0;ii<clusterSize;ii++)
		{
			Cluster c1 = clusterList.get(ii);
			for(int jj=ii+1;jj<clusterSize;jj++)
			{
				Cluster c2 = clusterList.get(jj);

				ArrayList<Point> pointsFromCluster1 = c1.getPoints();
				ArrayList<Point> pointsFromCluster2 = c2.getPoints();

				int size1 = pointsFromCluster1.size();
				for(int i=0;i<size1;i++)
				{
					Point p1 = pointsFromCluster1.get(i);

					int size2 =  pointsFromCluster2.size();
					for(int j=0;j<size2;j++)
					{
						Point p2 = pointsFromCluster2.get(j);
						double distance = getDistance(p1, p2);
						Distance d = new Distance(p1, p2, distance);
						distanceMap.put(distance, d);
					}

				}

			}
		}
		Entry<Double, Distance> pair = distanceMap.pollFirstEntry();
		pair.getValue().printDistanceDetails();
		return pair.getValue();
	}


	public Distance getMaxDistance(ArrayList<Cluster> clusterList)
	{
		TreeMap <Double, Distance> distanceMap = new TreeMap<Double, Distance>();
		int clusterSize = clusterList.size();
		
		for(int ii=0;ii<clusterSize;ii++)
		{
			Cluster c1 = clusterList.get(ii);
			for(int jj=ii+1;jj<clusterSize;jj++)
			{
				Cluster c2 = clusterList.get(jj);

				ArrayList<Point> pointsFromCluster1 = c1.getPoints();
				ArrayList<Point> pointsFromCluster2 = c2.getPoints();

				int size1 = pointsFromCluster1.size();
				for(int i=0;i<size1;i++)
				{
					Point p1 = pointsFromCluster1.get(i);

					int size2 =  pointsFromCluster2.size();
					for(int j=0;j<size2;j++)
					{
						Point p2 = pointsFromCluster2.get(j);
						double distance = getDistance(p1, p2);
						Distance d = new Distance(p1, p2, distance);
						distanceMap.put(distance, d);
					}

				}

			}
		}
		Entry<Double, Distance> pair = distanceMap.pollLastEntry();
		return pair.getValue();
	}

	public Point getAvge(Cluster c)
	{
		double meanX = 0.0;
		double meanY = 0.0;

		int size = c.getPoints().size();
		for(int i=0;i<size;i++)
		{
			Point p= c.getPoints().get(i);
			meanX = meanX + p.getxVal();
			meanY = meanY +p.getyVal();
		}

		meanX = meanX /size;
		meanY = meanY/size;

		Point center = new Point(c.getLabel()+"*", meanX, meanY);
		return center;
	}

	public Distance getAvgeDistance(ArrayList<Cluster> clusterList)
	{
		TreeMap <Double, Distance> distanceMap = new TreeMap<Double, Distance>();
		int clusterSize = clusterList.size();
		for(int ii=0;ii<clusterSize;ii++)
		{
			Cluster c1 = clusterList.get(ii);
			Point center1 = getAvge(c1);
			c1.setCenter(center1);
			clusterList.get(ii).setCenter(center1);
		}

		for(int ii=0;ii<clusterSize;ii++)
		{
			Cluster c1 = clusterList.get(ii);
			for(int jj=ii+1;jj<clusterSize;jj++)
			{
				Cluster c2 = clusterList.get(jj);

				double d = getDistance(c1.getCenter(), c2.getCenter());
				Distance dist = new Distance(c1.getCenter(), c2.getCenter(), d);
				distanceMap.put(d, dist);
			}
		}
		Entry<Double, Distance> pair = distanceMap.pollFirstEntry();
		//pair.getValue().printDistanceDetails();
		return pair.getValue();
	}

	public ArrayList<Cluster> getClustersList(int mode)
	{
		ArrayList<Cluster> localClusters = clusters;
		if(mode !=AVG)
		{
			while(localClusters.size() > 11)
			{
				Distance d = null;
				if(mode == MAX)
					d =  getMaxDistance(localClusters);
				else if(mode == MIN)
					d= getMinDistance(localClusters);

				Point p1 = d.getOne();
				Point p2 = d.getTwo();
				
				int index1 = getContainingCluster(localClusters, p1);
				int index2 = getContainingCluster(localClusters, p2);
				
				localClusters = combineClusters(localClusters, index1, index2);
			}
		}
		else
		{
			localClusters = clusters;
			while(localClusters.size() > 10)
			{
				Distance d = getAvgeDistance(localClusters);
				Point p1 = d.getOne();
				Point p2 = d.getTwo();
				int index1 = getCenterContainingCluster(localClusters, p1);
				int index2 = getCenterContainingCluster(localClusters, p2);
				localClusters = combineClusters(localClusters, index1, index2);
			}
		}
		return localClusters;
	}
	
	
	public ArrayList<String> getCities(String label)
	{
		ArrayList<String> cities = new ArrayList<String>();
		String [] names =  label.split(Utility.space);
		for(int i=0;i<names.length;i++)
		{
			cities.add(names[i]);
		}
		return cities;
	}
	
	public ArrayList<String> getClusters(String city)
	{
		ArrayList<Cluster> clusters = getClustersList(AVG);
		int size = clusters.size();
		
		for(int i=0;i<size;i++)
		{
			Cluster c = clusters.get(i);
			String label = c.getLabel();
			String [] names =  label.split(Utility.space);
			
			for(int j=0;j<names.length;j++)
			{
				//System.out.println("Name to compare:"+city);
				//System.out.println("Current Name:"+names[j]);
				if(names[j].equalsIgnoreCase(city))
				{
					ArrayList<String> cities = getCities(label);
					return cities;
				}
			}
		}
		return null;
	}

	public ArrayList<Cluster> combineClusters(ArrayList<Cluster> localClusters, int index1, int index2)
	{
		Cluster c1 = localClusters.get(index1);
		Cluster c2 = localClusters.get(index2);

		c1.setLabel(c1.getLabel()+" "+c2.getLabel());
		int size = c2.getPoints().size();
		for(int i =0;i<size;i++)
			c1.getPoints().add(c2.getPoints().get(i));

		localClusters.remove(index1);
		index2 = index2 -1;
		localClusters.remove(index2);
		localClusters.add(c1);
		return localClusters;

	}

	public int getContainingCluster(ArrayList<Cluster> clusterList, Point p1)
	{
		int size = clusterList.size();
		for(int i=0;i<size;i++)
		{
			int size2 = clusterList.get(i).getPoints().size();
			for(int j=0;j<size2;j++)
			{
				Point p2 = clusterList.get(i).getPoints().get(j);
				if(p1.getLabel().equalsIgnoreCase(p2.getLabel()))
					return i;
			}
		}
		return -1;
	}

	public int getCenterContainingCluster(ArrayList<Cluster> clusterList, Point p1)
	{
		int size = clusterList.size();
		for(int i=0;i<size;i++)
		{
			Cluster c = clusterList.get(i);
			if(c.getCenter().getLabel().equalsIgnoreCase(p1.getLabel()))
				return i;
		}
		return -1;
	}

	public static void main(String...strings)
	{
		HCluster clustering = new HCluster();
		clustering.parseLinesIntoPoints();
		ArrayList<String> cities = clustering.getClusters("Colombo");
		
		System.out.println("Cluster size:"+cities.size());
		for(int i=0;i<cities.size();i++)
		{
			System.out.println(cities.get(i));
		}

	}
}
