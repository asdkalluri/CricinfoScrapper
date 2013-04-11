package process;

import input.Reader;

import java.util.ArrayList;
import java.util.Random;

import clustering.HCluster;

import stubs.Match;
import sun.security.krb5.internal.crypto.CksumType;
import util.Utility;
import db.ConvertDAO;

public class Processor {

	Reader reader = new Reader();
	Match m = new Match();
	ConvertDAO dbConnection = new ConvertDAO();
	HCluster cluster = new HCluster();
	public final int stadiumApproach =1;
	public final int seriesApproach =2;
	public final int randomVal =10;
	ArrayList<Integer> team1ScoreList = new ArrayList<Integer>();
	ArrayList<Integer> team2ScoreList = new ArrayList<Integer>();
	public final String by = "by";
	public final String against = "against";

	public int randTestReturn(int score)
	{
		if(score <5)
		{
			Random g = new Random();
			return g.nextInt(randomVal);
		}
		return score;
	}

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

	public double getPythagoreanValue(int runsScoredBy, int runsScoredAgainst)
	{
		int p =2;
		double probability = Math.pow(runsScoredBy, 2)/ (Math.pow(runsScoredBy, p) + Math.pow(runsScoredAgainst, p));
		return probability;
	}
	
	
	public int processPythApproachForTeam(int teamId)
	{
		int runsScoredByTeam = dbConnection.getRunsScored(teamId,by);
		int runsScoredAgainstTeam = dbConnection.getRunsScored(teamId,against);
		double probTeam = getPythagoreanValue(runsScoredByTeam, runsScoredAgainstTeam);
		
		int probNormalizedOver10Matches = (int) (probTeam * 10);
		int matchesWonOutOf5 = dbConnection.getNumberOfMatchesWonInlast5Matches(teamId);
		int matchesToBeWonInNext5 = probNormalizedOver10Matches - matchesWonOutOf5;
		return matchesToBeWonInNext5;
	}
	public void processPythaGoreanApproach(String roster1, String roster2)
	{
		int teamId1 = dbConnection.getTeamId((m.getTeam1()));
		int teamId2 = dbConnection.getTeamId((m.getTeam2()));
		
		int winningChancesInNext5ForTeam1 = processPythApproachForTeam(teamId1);
		int winningChancesInNext5ForTeam2 = processPythApproachForTeam(teamId2);
		
		if(winningChancesInNext5ForTeam1 > winningChancesInNext5ForTeam2)
		{
			System.out.println("Winner:"+teamId1);
		}
		if(winningChancesInNext5ForTeam1 < winningChancesInNext5ForTeam2)
		{
			System.out.println("Winner:"+teamId2);
		}
		else
		{
			System.out.println("TIE");
		}
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
			/*System.out.println("player:"+playerName);
			System.out.println("Score1:"+score1);
			System.out.println("Score2:"+score2);
			System.out.println("Normalized score:"+score);*/
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
			/*System.out.println("player:"+playerName);
			System.out.println("Score1:"+score1);
			System.out.println("Score2:"+score2);
			System.out.println("Normalized score:"+score);*/
			team2Score = team2Score+ score;
		}		

		System.out.println("Predicted Scores:");
		System.out.println(teamId1+":"+team1Score);
		System.out.println(teamId2+":"+team2Score);

		team1ScoreList.add(team1Score);
		team2ScoreList.add(team2Score);
	}

	public void processClusteringApproach(String roster1, String roster2, int clusteringApproach)
	{
		int teamId1 = dbConnection.getTeamId((m.getTeam1()));
		int teamId2 = dbConnection.getTeamId((m.getTeam2()));
		ArrayList<String> clusterMembers = new  ArrayList<String>();

		if(clusteringApproach == stadiumApproach)
		{
			String city = m.getLocation().trim();
			clusterMembers = cluster.getClusters("Colombo");
		}
		else
		{
			String series = m.getSeries();
			clusterMembers.add(series);
		}

		ArrayList<String> team1Players = Utility.readFile(roster1);
		ArrayList<String> team2Players = Utility.readFile(roster2);

		int size = team1Players.size();
		int team1Score = 0;
		for(int i=0;i<size;i++)
		{
			String playerName = team1Players.get(i);
			int pid = dbConnection.getPlayerId(playerName);

			int score = dbConnection.getMeanScoreForBatsman(pid, clusteringApproach, clusterMembers);
			score = randTestReturn(score);

			/*System.out.println("player:"+playerName);
			System.out.println("Score:"+score);*/


			team1Score = team1Score+ score;
		}

		size = team2Players.size();
		int team2Score = 0;
		for(int i=0;i<size;i++)
		{
			String playerName = team2Players.get(i);
			int pid = dbConnection.getPlayerId(playerName);
			int score = dbConnection.getMeanScoreForBatsmanAgainst(pid, teamId2,teamId1);
			score = randTestReturn(score);
			/*System.out.println("player:"+playerName);
			System.out.println("Score:"+score);*/
			team2Score = team2Score+ score;
		}

		System.out.println("Predicted Scores:");
		System.out.println(teamId1+":"+team1Score);
		System.out.println(teamId2+":"+team2Score);

		team1ScoreList.add(team1Score);
		team2ScoreList.add(team2Score);
	}


	public int getMeanScore(ArrayList<Integer> list)
	{
		int size = list.size();
		int sum = 0;
		for(int i=0;i<size;i++)
		{
			sum = sum + list.get(i);
		}
		return (sum)/size;
	}

	public void printFinalPredictions()
	{
		int teamId1 = dbConnection.getTeamId((m.getTeam1()));
		int teamId2 = dbConnection.getTeamId((m.getTeam2()));
		System.out.println("Final Predictions:");
		int score1 =getMeanScore(team1ScoreList);
		int score2 =getMeanScore(team2ScoreList);
		System.out.println(teamId1+":"+score1);
		System.out.println(teamId2+":"+score2);	
	
	}

	public void processMatch(String matchFile, String roster1, String roster2)
	{
		cluster.parseLinesIntoPoints();
		m = readMatchDetails(matchFile);
		//m.printMatchDetails();

		System.out.println("Solving for teamBased approach:");
		processTeamBasedApproach(roster1,roster2);

		System.out.println("Solving clustering based approach: Stadiums");
		processClusteringApproach(roster1, roster2, 1);

		System.out.println("Solving clustering based approach: Tournaments");
		processClusteringApproach(roster1, roster2, 2);

	}

	public static void main(String...strings)
	{
		Processor p = new Processor();
		String matchFile = "files/matchInfo";
		p.processMatch(matchFile, "files/Team1Roster", "files/Team2Roster");
	}
}
