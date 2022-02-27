import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class AdminViewDao {
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
	
	public static int getId(String name) throws SQLException {
		int items = 0;
		Connection conn = getConnection();
		String sql = "select id from voto.session where name=?" ;
		PreparedStatement query = conn.prepareStatement(sql);
		query.setString(1, name);
        ResultSet res = query.executeQuery();
        while ( res.next() ) {
        	items = res.getInt("id");
        }
		return items;
	}
	
	public static int getLastId() throws SQLException {
		int items = 0;
		Connection conn = getConnection();
		String sql = "select id from voto.session" ;
		PreparedStatement query = conn.prepareStatement(sql);
        ResultSet res = query.executeQuery();
        while ( res.next() ) {
        	items = res.getInt("id");
        }
		return items;
	}
	
	public static void createSession(String name, String modeVote, String modeWin) throws SQLException {
		Connection conn = getConnection();
		PreparedStatement query1 = conn.prepareStatement("insert into voto.session(name, modeVote, modeWin) values(?, ?, ?)");
		//query1.setInt(1, getLastId()+1);
        query1.setString(1, name);
        query1.setString(2, modeVote);
        query1.setString(3, modeWin);
        @SuppressWarnings("unused")
		boolean res = query1.execute();
	}
	
	public static void modifySession(String oldName, String name, String modeVote, String modeWin, boolean isOpen) throws SQLException {
		Connection conn = getConnection();
		PreparedStatement query1 = conn.prepareStatement("update voto.session set name=?, modeVote=?, modeWin=?, isOpen=? where id=?");
        query1.setString(1, name);
        query1.setString(2, modeVote);
        query1.setString(3, modeWin);
        query1.setBoolean(4, isOpen);
        query1.setInt(5, getId(oldName));
        @SuppressWarnings("unused")
		int res = query1.executeUpdate();
	}
	
	public static String getModeVote(String name) throws SQLException {
		String s = "";
		Connection conn = getConnection();
		String sql = "select modeVote from voto.session where name=?" ;
		PreparedStatement query = conn.prepareStatement(sql);
		query.setString(1, name);
		ResultSet r = query.executeQuery();
		while(r.next()) {
			s = r.getString("modeVote");
		}
		return s;
	}
	
	public static String getModeWin(String name) throws SQLException {
		String s = "";
		Connection conn = getConnection();
		String sql = "select modeWin from voto.session where name=?" ;
		PreparedStatement query = conn.prepareStatement(sql);
		query.setString(1, name);
		ResultSet r = query.executeQuery();
		while(r.next()) {
			s = r.getString("modeWin");
		}
		return s;
	}
	
	public static void addCandidate(String session, String candidate) throws SQLException {
		Connection conn = getConnection();
		PreparedStatement query1 = conn.prepareStatement("insert into voto.candidates(idSession, name) values(?, ?)");
		query1.setInt(1, getId(session));
        query1.setString(2, candidate);
        @SuppressWarnings("unused")
		boolean res = query1.execute();
	}
	
	public static void getSessions(MenuButton scegliSessione) throws SQLException {
		scegliSessione.getItems().clear();
		List<String> l = new ArrayList<>();
		Connection conn = getConnection();
		String sql = "select name from voto.session" ;
		PreparedStatement query = conn.prepareStatement(sql);
		ResultSet r = query.executeQuery();
		while(r.next()) {
			l.add(r.getString("name"));
		}
		
		for (String i:l) {
    		MenuItem session = new MenuItem(i);
        	scegliSessione.getItems().add(session);
    	}
	}
	
	public static List<List<String>> getVote(String session) throws SQLException {
		Connection conn = getConnection();
		String sql = "select name, preferenza from voto.candidates where idsession=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, getId(session));
		ResultSet r = stmt.executeQuery();
		List<List<String>> le = new ArrayList<>();
		
		while(r.next()) {
			List<String> li = new ArrayList<>();
			li.add(r.getString("name"));
			li.add(String.valueOf(r.getInt("preferenza")));
			le.add(li);
		}
		return le;
	}

	public static String getWinner(String name) throws SQLException {
		String w = "";
		
		return w;
	}
}
