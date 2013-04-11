package stubs;

import java.util.ArrayList;

import clustering.HCluster;
import db.ConvertDAO;


public class Test {

	
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
