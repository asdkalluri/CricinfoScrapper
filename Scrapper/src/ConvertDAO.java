import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


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
		return 0;
		
     }
}
