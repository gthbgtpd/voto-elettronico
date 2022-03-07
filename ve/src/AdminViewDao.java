import java.sql.Connection;
import java.sql.Date;
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
	
	/*public static int getId(String name) throws SQLException {
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
	}*/
	
	public static int getId(String table, String name) throws SQLException {
		int items = 0;
		Connection conn = getConnection();
		String sql = "select id from "+table+" where name=?" ;
		PreparedStatement query = conn.prepareStatement(sql);
		query.setString(1, name);
        ResultSet res = query.executeQuery();
        while ( res.next() ) {
        	items = res.getInt("id");
        }
		return items;
	}
	
	public static int getLastId(String table) throws SQLException {
		int items = 0;
		Connection conn = getConnection();
		String sql = "select id from "+table ;
		PreparedStatement query = conn.prepareStatement(sql);
        ResultSet res = query.executeQuery();
        while ( res.next() ) {
        	items = res.getInt("id");
        }
		return items;
	}
	
	public static void createSession(String name, String modeVote, String modeWin) throws SQLException {
		Connection conn = getConnection();
		PreparedStatement query1 = conn.prepareStatement("insert into voto.session(id, name, modeVote, modeWin) values(?, ?, ?, ?)");
		query1.setInt(1, getLastId("voto.session")+1);
        query1.setString(2, name);
        query1.setString(3, modeVote);
        query1.setString(4, modeWin);
        @SuppressWarnings("unused")
		boolean res = query1.execute();
	}
	
	public static void modifySession(String oldName, String name, String modeVote, String modeWin, Date dataApertura, Date dataChiusura) throws SQLException {
		Connection conn = getConnection();
		PreparedStatement query1 = conn.prepareStatement("update voto.session set name=?, modeVote=?, modeWin=?, dataapertura=?, datachiusura=? where id=?");
        query1.setString(1, name);
        query1.setString(2, modeVote);
        query1.setString(3, modeWin);
        query1.setDate(4, dataApertura);
        query1.setDate(5, dataChiusura);
        query1.setInt(6, getId("voto.session", oldName));
        query1.executeUpdate();
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
	
	public static void addCandidate(String session, String candidate, boolean isGroup) throws SQLException {
		Connection conn = getConnection();
		PreparedStatement query1 = conn.prepareStatement("insert into voto.candidates(id, idSession, name, isParty) values(?, ?, ?, ?)");
		int idCandidate = getLastId("voto.candidates")+1;
		int idSession =  getId("voto.session", session);
		query1.setInt(1, idCandidate);
		query1.setInt(2, idSession);
        query1.setString(3, candidate);
        query1.setBoolean(4, isGroup);
        PreparedStatement query2 = conn.prepareStatement("insert into voto.vote(idsession, idcandidates, preferenza) values(?, ?, 0)");
        query2.setInt(1, idSession);
        query2.setInt(2, idCandidate);
        @SuppressWarnings("unused")
		boolean res = query1.execute();
        res = query2.execute();
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
		String sql = "select s.name, c.name, v.preferenza from voto.vote as v join voto.session as s on v.idsession=s.id join voto.candidates as c on v.idcandidates=c.id where v.idsession=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, getId("voto.session", session));
		ResultSet r = stmt.executeQuery();
		List<List<String>> le = new ArrayList<>();
		
		while(r.next()) {
			List<String> li = new ArrayList<>();
			li.add(r.getString(1));
			li.add(r.getString(2));
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
