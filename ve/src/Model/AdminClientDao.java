import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminClientDao {
	
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
	
	public static void putVote(Votable candidato, Integer value) throws SQLException {
		Connection conn = getConnection();
		int id=0;
		PreparedStatement query = conn.prepareStatement("select c.id from voto.vote as v join voto.candidates as c on v.idcandidates=c.id where c.name=?");
		query.setString(1, candidato.toString());
		ResultSet res = query.executeQuery();
        while ( res.next() ) {
        	id = res.getInt(1);
        }
		
		
		PreparedStatement query1 = conn.prepareStatement("update voto.vote set preferenza=? where id=?");
        query1.setInt(1, value);
        query1.setInt(2, id);
        @SuppressWarnings("unused")
		int r = query1.executeUpdate();
        return;
	}

}
