package db;

public class PlayerRecord {

	int playerID;
	String playerName;
	int runDetails[];//runs_scored	minutes	balls_faced	fours	sixes (5)
	String dismissal;
	int wicketTaker;
	float oversBowled;
	int bowlingDetails[];//	maidens	runs_conceded	wickets	wide	NB (5)
	int catches;
	int stumps;
	int runouts;
	boolean isWK,isCap;
	
	public PlayerRecord()
	{
		runDetails = new int[5];
		dismissal = "";
		bowlingDetails = new int[5];
		isCap=false;
		isWK = false;
	}
	public void print()
	{
		System.out.print(playerID+"\t");
		for(int i=0;i<runDetails.length;i++)
			System.out.print(runDetails[i]+"\t");
		System.out.print(dismissal+"\t"+wicketTaker+"\t"+oversBowled+"\t");
		for(int i=0;i<bowlingDetails.length;i++)
			System.out.print(bowlingDetails[i]+"\t");
		System.out.print(catches+"\t"+stumps+"\t"+runouts+"\t"+isWK+"\t"+isCap);
		
	}
}
