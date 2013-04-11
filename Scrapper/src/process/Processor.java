package process;

import input.Reader;

import java.util.ArrayList;

import clustering.HCluster;

import stubs.Match;
import util.Utility;
import db.ConvertDAO;

public class Processor {

	Reader reader = new Reader();
	Match m = new Match();
	ConvertDAO dbConnection = new ConvertDAO();
	HCluster cluster = new HCluster();

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

	public void processTeamBasedApproach(String roster1, String roster2)
	{
		int teamId1 = dbConnection.getTeamId((m.getTeam1()));
		int teamId2 = dbConnection.getTeamId((m.getTeam2()));

		int team1Strength = getTeamStrength(m.getTeam1(), m.getTeam2());
		int team2Strength = getTeamStrength(m.getTeam2(), m.getTeam1());

		ArrayList<String> team1Players = Utility.readFile(roster1);
		ArrayList<String> team2Players = Utility.readFile(roster2);

		int size = team1Players.size();
		int team1Score = 0;
		for(int i=0;i<size;i++)
		{
			String playerName = team1Players.get(i);
			int pid = dbConnection.getPlayerId(playerName);
			int score1 = dbConnection.getMeanScoreForBatsmanAgainst(pid, teamId1, teamId2);
			int score2 = dbConnection.getMeanScoreForBatsmanInRecentTime(pid);
			int score = (score1 + score2)/2;
			System.out.println("player:"+playerName);
			System.out.println("Score1:"+score1);
			System.out.println("Score2:"+score2);
			System.out.println("Normalized score:"+score);
			team1Score = team1Score+ score;
		}

		size = team2Players.size();
		int team2Score = 0;
		for(int i=0;i<size;i++)
		{
			String playerName = team2Players.get(i);
			int pid = dbConnection.getPlayerId(playerName);
			int score1 = dbConnection.getMeanScoreForBatsmanAgainst(pid, teamId2,teamId1);
			int score2 = dbConnection.getMeanScoreForBatsmanInRecentTime(pid);
			int score = (score1 + score2)/2;
			System.out.println("player:"+playerName);
			System.out.println("Score1:"+score1);
			System.out.println("Score2:"+score2);
			System.out.println("Normalized score:"+score);
			team2Score = team2Score+ score;
		}

		/*double	t1Score =  team1Score * (team1Strength / (double)team2Strength);
		double t2Score = team2Score * (team2Strength / (double)team1Strength);*/
		
		
		System.out.println("Predicted Scores:");
		System.out.println(teamId1+":"+team1Score);
		System.out.println(teamId2+":"+team2Score);
	}

	public void processClusteringApproach(String roster1, String roster2, int clusteringApproach)
	{
		int teamId1 = dbConnection.getTeamId((m.getTeam1()));
		int teamId2 = dbConnection.getTeamId((m.getTeam2()));

		String city = m.getLocation();
		ArrayList<String> clusterMembers = cluster.getClusters(city);
		ArrayList<String> team1Players = Utility.readFile(roster1);
		ArrayList<String> team2Players = Utility.readFile(roster2);

		int size = team1Players.size();
		int team1Score = 0;
		for(int i=0;i<size;i++)
		{
			String playerName = team1Players.get(i);
			int pid = dbConnection.getPlayerId(playerName);
			
			int score = dbConnection.getMeanScoreForBatsman(pid, clusteringApproach, clusterMembers);
			System.out.println("player:"+playerName);
			System.out.println("Score:"+score);
			team1Score = team1Score+ score;
		}

		size = team2Players.size();
		int team2Score = 0;
		for(int i=0;i<size;i++)
		{
			String playerName = team2Players.get(i);
			int pid = dbConnection.getPlayerId(playerName);
			int score1 = dbConnection.getMeanScoreForBatsmanAgainst(pid, teamId2,teamId1);
			int score2 = dbConnection.getMeanScoreForBatsmanInRecentTime(pid);
			int score = (score1 + score2)/2;
			System.out.println("player:"+playerName);
			System.out.println("Score1:"+score1);
			System.out.println("Score2:"+score2);
			System.out.println("Normalized score:"+score);
			team2Score = team2Score+ score;
		}

		/*double	t1Score =  team1Score * (team1Strength / (double)team2Strength);
		double t2Score = team2Score * (team2Strength / (double)team1Strength);*/
		
		
		System.out.println("Predicted Scores:");
		System.out.println(teamId1+":"+team1Score);
		System.out.println(teamId2+":"+team2Score);
	}

	public void processMatch(String matchFile, String roster1, String roster2)
	{
		m = readMatchDetails(matchFile);
		//m.printMatchDetails();
		processTeamBasedApproach(roster1,roster2);
		processClusteringApproach(roster1, roster2, 1);
		


	}

	public static void main(String...strings)
	{
		Processor p = new Processor();
		String matchFile = "files/matchInfo";
		p.processMatch(matchFile, "files/Team1Roster", "files/Team2Roster");
	}
}
