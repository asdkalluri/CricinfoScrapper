package db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.Statement;


public class ConvertDAO {
     public Connection getConnection() throws SQLException {
    	 Connection conn = null;
         conn = ConnectionFactory.getInstance().getConnection();
         return conn;
     }
     
     public int getPlayerId(String name)
     {
    	 Connection conn = null;
         PreparedStatement ps = null;
         ResultSet rs = null;
    	 try{
    		 conn = getConnection();
    		 ps = conn.prepareStatement("SELECT idPlayer from player WHERE name = ?");
    		 ps.setString(1,name);
    		 rs=ps.executeQuery();
    		 if(rs.next())
    		 {
    			 return rs.getInt(1);
    		 }
    	 }catch(SQLException e){
    		 e.printStackTrace();
    	 }finally {
            try {
                if (ps != null)
                        ps.close();
                if (conn != null)
                        conn.close();
	        } catch (SQLException e) {
	                e.printStackTrace();
	        } catch (Exception e) {
	                e.printStackTrace();
	        }
		}
    	System.err.println("Player "+name+" not found");
		return 0;
		
     }

     public int getTeamId(String teamName)
     {
    	 Connection conn = null;
         PreparedStatement ps = null;
         ResultSet rs = null;
    	 try{
    		 conn = getConnection();
    		 ps = conn.prepareStatement("SELECT idTeam from team WHERE name = ?");
    		 ps.setString(1,teamName);
    		 rs=ps.executeQuery();
    		 if(rs.next())
    		 {
    			 return rs.getInt(1);
    		 }
    	 }catch(SQLException e){
    		 e.printStackTrace();
    	 }finally {
            try {
                if (ps != null)
                        ps.close();
                if (conn != null)
                        conn.close();
	        } catch (SQLException e) {
	                e.printStackTrace();
	        } catch (Exception e) {
	                e.printStackTrace();
	        }
		}
    	System.err.println("Team "+teamName+" not found");
    	return -1;
     }
     
     /**
      * Returns number of matches worn by team1 against team2
      * @param teamId1
      * @param teamId2
      * @return
      */
     public int getNoMatchesWonBy(int teamId1,int teamId2)
     {
    	 Connection conn = null;
    	 PreparedStatement ps = null;
         ResultSet rs = null;
    	 try{
    		 conn = getConnection();
    		 ps = conn.prepareStatement("SELECT COUNT(*) from `match` WHERE (team1 = ? OR team2= ?) AND (team1 = ? OR team2= ?) AND winner= ?");
    		 ps.setInt(1, teamId1);
    		 ps.setInt(2, teamId1);
    		 ps.setInt(3, teamId2);
    		 ps.setInt(4, teamId2);
    		 ps.setInt(5, teamId1);
    		
    		 rs=ps.executeQuery();
    		 if(rs.next())
    		 {
    			 return rs.getInt(1);
    		 }
    	 }catch(SQLException e){
    		 e.printStackTrace();
    	 }finally {
            try {
                if (ps != null)
                        ps.close();
                if (conn != null)
                        conn.close();
	        } catch (SQLException e) {
	                e.printStackTrace();
	        } catch (Exception e) {
	                e.printStackTrace();
	        }
		}
    	 return -1;
     }
     
     public int getNumberOfMatchesWonInlast5Matches(int teamId)
     {
    	 Connection conn = null;
         PreparedStatement ps = null;
         ResultSet rs = null;
    	 try{
    		 conn = getConnection();
    		 ps = conn.prepareStatement("SELECT M.winner FROM `match` M  where M.team1=? or M.team2=? order by M.date desc");
    		 ps.setInt(1,teamId);
    		 ps.setInt(2,teamId);
    		 rs=ps.executeQuery();
    		 int count = 0;
    		 int matchesWon=0;
    		 while(rs.next()&&count<5)
    		 {
    			 if(rs.getInt(1)==teamId)
    				 matchesWon++;
    			 count++;
    		 }
    		 return matchesWon;
    	 }catch(SQLException e){
    		 e.printStackTrace();
    	 }finally {
            try {
                if (ps != null)
                        ps.close();
                if (conn != null)
                        conn.close();
	        } catch (SQLException e) {
	                e.printStackTrace();
	        } catch (Exception e) {
	                e.printStackTrace();
	        }
		}
    	 return -1;
     }
     
   
     
     public int getMeanScoreForBatsmanInRecentTime(int playerId)
     {
    	 Connection conn = null;
         PreparedStatement ps = null;
         ResultSet rs = null;
    	 try{
    		 conn = getConnection();
    		 ps = conn.prepareStatement("SELECT runsScored from `scoretable` S, `match` M  WHERE S.idMatch = M.idmatch AND  S.idPlayer = ? and M.date < ?" +
    		 		"order by M.date DESC");
    		 ps.setInt(1,playerId);
    		 Date d = new Date();
    		 SimpleDateFormat sdf = new  SimpleDateFormat("yyyy/mm/dd");
    		 ps.setString(2, sdf.format(d));
    		 rs=ps.executeQuery();
    		 int count = 0;
    		 int runsScored=0;
    		 while(rs.next()&&count<5)
    		 {
    			 runsScored+=rs.getInt(1);
    			 count++;
    		 }
    		 return runsScored/count;
    	 }catch(SQLException e){
    		 e.printStackTrace();
    	 }finally {
            try {
                if (ps != null)
                        ps.close();
                if (conn != null)
                        conn.close();
	        } catch (SQLException e) {
	                e.printStackTrace();
	        } catch (Exception e) {
	                e.printStackTrace();
	        }
		}
    	 return -1;
     }

     /**
      * 
      * @param pid
      * @param playersTeamId
      * @param opponentTeamId
      * @return
      */
     public int getMeanScoreForBatsmanAgainst(int pid, int playersTeamId, int opponentTeamId)
     {
    	 Connection conn = null;
    	 PreparedStatement ps = null;
         ResultSet rs = null;
         ArrayList<Integer> matches = new ArrayList<Integer>();
    	 try{
    		 conn = getConnection();
    		 ps = conn.prepareStatement("SELECT idMatch FROM `match` where team1 in (?,?) AND team2 in (?,?)");
    		 ps.setInt(1, playersTeamId);
    		 ps.setInt(2, playersTeamId);
    		 ps.setInt(3, opponentTeamId);
    		 ps.setInt(4, opponentTeamId);
    		
    		 rs=ps.executeQuery();
    		 while(rs.next())
    		 {
    			 matches.add(rs.getInt(1));
    		 }
    		 
    		 
    		 int playerScore =0;
    		 for(int i=0;i<matches.size();i++)
    		 {
    			 int matchId = matches.get(i);
    			 
    			 conn = getConnection();
        		 ps = conn.prepareStatement("SELECT runsScored FROM `scoretable` where idPlayer=? AND idMatch=?");
        		 ps.setInt(1, pid);
        		 ps.setInt(2, matchId);
        		 rs=ps.executeQuery();
        		 if(rs.next())
        		 {
        			 int score = rs.getInt(1);
        			 System.out.println(score);
        			 playerScore = playerScore + score;
        		 }
    		 }
    		 
    		 playerScore = playerScore /matches.size();
    		 return playerScore;
    	 }catch(SQLException e){
    		 e.printStackTrace();
    	 }finally {
            try {
                if (ps != null)
                        ps.close();
                if (conn != null)
                        conn.close();
	        } catch (SQLException e) {
	                e.printStackTrace();
	        } catch (Exception e) {
	                e.printStackTrace();
	        }
		}
    	 return -1;
     }
     
     /**
      * 
      * @param playerId
      * @param clusterType
      * @param clusterMembers
      * @return
      */
     public int getMeanScoreForBatsman(int playerId, int clusterType, ArrayList<String> clusterMembers)
     {
    	 return -1;
     }
}
