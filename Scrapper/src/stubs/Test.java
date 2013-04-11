package stubs;

import db.ConvertDAO;


public class Test {

	
	public static void main(String...strings)
	{
		ConvertDAO dbcon =new ConvertDAO();
		int n = dbcon.getMeanScoreForBatsmanAgainst(543,2,1);
		System.out.println(n);
	}
}
