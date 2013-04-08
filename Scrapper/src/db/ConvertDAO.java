package db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ConvertDAO {
     public Connection getConnection() throws SQLException {
    	 Connection conn = null;
         conn = ConnectionFactory.getInstance().getConnection();
         return conn;
     }
     
     public int lookupID(String name)
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
     
     public int getNoMatchesWonBy(int teamId1,int teamId2)
     {
    	 return -1;
     }
     
     public int getNumberOfMatchesWonInlast5Matches(int teamId)
     {
    	 return -1;
     }
     
     public int getPlayerId(String name)
     {
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

     public int getMeanScoreForBatsmanAgainst(int teamId)
     {
    	 return -1;
     }
     
     public int getMeanScoreForBatsman(int playerId, int clusterType, ArrayList<String> clusterMembers)
     {
    	 return -1;
     }
}
