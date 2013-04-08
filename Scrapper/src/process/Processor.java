package process;

import input.Reader;

import java.util.ArrayList;

import stubs.Match;
import util.Utility;
import db.ConvertDAO;

public class Processor {

	Reader reader = new Reader();
	Match m = new Match();
	ConvertDAO dbConnection = new ConvertDAO();

	public Match readMatchDetails(String matchFile)
	{
		ArrayList<String> matchDetails = Utility.readFile(matchFile);
		System.out.println("Size:"+matchDetails.size());

		Match m = new Match();
		String tokens [] = Utility.splitLine(matchDetails.get(0));
		m.setTeam1(tokens[1]);
		tokens = Utility.splitLine(matchDetails.get(1));
		m.setTeam2(tokens[1]);
		tokens  = Utility.splitLine(matchDetails.get(2));
		m.setLocation(tokens[1]);
		tokens  = Utility.splitLine(matchDetails.get(3));
		m.setSeries(tokens[1]);
		return m;
	}

	
	public int getTeamStrength(String team1, String team2)
	{
		 int teamId1 = dbConnection.getTeamId(team1);
		 int teamId2 = dbConnection.getTeamId(team2);
		 int team1WonH2H = dbConnection.getNoMatchesWonBy(teamId1, teamId2);
		 int team1WonLast5 = dbConnection.getNumberOfMatchesWonInlast5Matches(teamId1);
		 return (team1WonH2H +team1WonLast5);
		 
	}
	
	public void processTeamBasedApproach()
	{
		int team1Strength = getTeamStrength(m.getTeam1(), m.getTeam2());
		int team2Strength = getTeamStrength(m.getTeam2(), m.getTeam1());

		//ArrayList<String> team1Players = Utility.readFile(name)
	}

	
	public void processMatch(String matchFile, String roster1, String roster2)
	{
		m = readMatchDetails(matchFile);
		//m.printMatchDetails();
		
	}

	public static void main(String...strings)
	{
		Processor p = new Processor();
		String matchFile = "files/matchInfo";
		p.processMatch(matchFile, "_", "_");
	}
}
