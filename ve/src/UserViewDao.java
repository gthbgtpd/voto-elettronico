import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class UserViewDao {
	
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

	public static String getMode(String sessione) throws SQLException {
		String s = "";
		Connection conn = getConnection();
		String sql = "select s.modevote from voto.session as s where s.name=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, sessione);
		ResultSet r = stmt.executeQuery();
		while(r.next()) {
			s = r.getString("modevote");
		}
		return s;
	}

	public static ObservableList<String> getCandidates(String sessione) throws SQLException {
		ObservableList<String> data = FXCollections.observableArrayList();
		Connection conn = getConnection();
		String sql = "select c.name from voto.candidates as c join voto.session as s on c.idsession=s.id where s.name=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, sessione);
		ResultSet r = stmt.executeQuery();
		while(r.next()) {
			data.add(r.getString(1));
		}
		return data;
	}

	public static ObservableList<String> getCandidatesFromParty(String partito) throws SQLException {
		int idp = 0;
		Connection conn = getConnection();
		String sql1 = "select c.id from voto.candidates as c where c.name=?";
		PreparedStatement stmt1 = conn.prepareStatement(sql1);
		stmt1.setString(1, partito);
		ResultSet r1 = stmt1.executeQuery();
		while(r1.next()) {
			idp = r1.getInt(1);
		}
		
		ObservableList<String> data = FXCollections.observableArrayList();
		String sql = "select c.name from voto.candidates as c join voto.partytable as p on p.idpartymember=c.id where p.idparty=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, idp);
		ResultSet r = stmt.executeQuery();
		while(r.next()) {
			data.add(r.getString(1));
		}
		return data;
	}

	public static ObservableList<String> getParty(String sessione) throws SQLException {
		ObservableList<String> data = FXCollections.observableArrayList();
		Connection conn = getConnection();
		String sql = "select c.name from voto.candidates as c  join voto.session as s on c.idsession=s.id where s.name=? and c.isparty=true";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, sessione);
		ResultSet r = stmt.executeQuery();
		while(r.next()) {
			data.add(r.getString(1));
		}
		return data;
	}

}
