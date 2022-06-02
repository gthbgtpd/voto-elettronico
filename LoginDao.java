
import java.sql.*;

public class LoginDao {

	private static Connection getConnection() throws SQLException {
	  	final String url = "jdbc:postgresql://localhost/votoelettronico?user=root&password=rootroot";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

  public static String getPassword(String username){
    String password = null;
    try {
    	Connection c = getConnection();
        PreparedStatement query = c.prepareStatement("select * from voto.users where username=?");
        query.setString(1, username);
        ResultSet res = query.executeQuery();
        while ( res.next() ) {
        password = res.getString("password");
        }
      
    } catch (SQLException e) {
    	System.out.println("SQLException: " + e.getMessage());
    	System.out.println("SQLState: " + e.getSQLState());
    	System.out.println("VendorError: " + e.getErrorCode());
    }
    return password;
  }
  
  public static String getType(String username){
	    String type = null;
	    try {
	    	Connection c = getConnection();
	        PreparedStatement query = c.prepareStatement("select * from voto.users where username=?");
	        query.setString(1, username);
	        ResultSet res = query.executeQuery();
	        while ( res.next() ) {
	        	type = res.getString("type");
	        }
	      
	    } catch (SQLException e) {
	    	System.out.println("SQLException: " + e.getMessage());
	    	System.out.println("SQLState: " + e.getSQLState());
	    	System.out.println("VendorError: " + e.getErrorCode());
	    }
	    return type;
	  }

  public static int getSalt(String username){
    int salt = 0;
    try {
    	Connection c = getConnection();
        PreparedStatement query = c.prepareStatement("select * from voto.users where username=?");
        query.setString(1, username);
        ResultSet res = query.executeQuery();
        while ( res.next() ) {
        salt = res.getInt("id");
        }
      
    } catch (SQLException e) {
    	System.out.println("SQLException: " + e.getMessage());
    	System.out.println("SQLState: " + e.getSQLState());
    	System.out.println("VendorError: " + e.getErrorCode());
    }
    return salt;
  }

}

