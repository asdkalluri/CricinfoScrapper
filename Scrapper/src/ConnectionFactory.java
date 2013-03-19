import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	//private String driverClassName = "com.mysql.jdbc.Driver";
	private String connectionUrl = "jdbc:mysql://localhost:3306/dataminingcricket";
	private String user = "test";
	private String pwd = "test";

	private static ConnectionFactory connectionFactory = null;

	private ConnectionFactory() {
		try {
			Driver myDriver = new com.mysql.jdbc.Driver();
			DriverManager.registerDriver( myDriver );
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException {
		Connection conn = null;
		conn = DriverManager.getConnection(connectionUrl, user, pwd);
		return conn;
	}

	public static ConnectionFactory getInstance() {
		if (connectionFactory == null) {
			connectionFactory = new ConnectionFactory();
		}
		return connectionFactory;
	}
}