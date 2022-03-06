import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import Model.Candidato;
import Model.Gruppo;
import Model.SessioneVoto;
import Model.SessioneVotoFactory;
import Model.Votable;

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
	
	public static SessioneVoto getVotingSession(String nameSession) throws SQLException {
		Connection con = getConnection();
		SessioneVotoFactory sf = new SessioneVotoFactory();
		String firstQuery = "SELECT * FROM voto.session AS s WHERE s.name = ?";
		PreparedStatement s = con.prepareStatement(firstQuery);
		s.setString(1, nameSession);
		ResultSet res = s.executeQuery();
		int idSessione = 1; // valore di default nel caso il nome sia scorretto 
		while(res.next()) {
			idSessione = res.getInt("id");
			String modeVote = res.getString("modevote");
			boolean isOpen = res.getBoolean("isopen");
			String modeWin = res.getString("modewin");
			boolean scrutinyPhase = res.getBoolean("fasescrutinio");
			Date beginningDate = res.getDate("dataapertura");
			Date endingDate = res.getDate("datachiusura");
			sf.setId(idSessione);
			sf.setDefinizioneVincitore(modeWin);
			sf.setBeginningDate(beginningDate);
			sf.setEndingDate(endingDate);
			sf.setScrutinyPhase(scrutinyPhase);
			sf.setOpen(isOpen);
			sf.setModalitaVotoName(modeVote);
		}
		s.close();
		res.close();
		String secondQuery = "SELECT * FROM voto.candidates as c WHERE c.idsession = ?";
		s = con.prepareStatement(secondQuery);
		s.setInt(1, idSessione);
		res = s.executeQuery();
		Set<Votable> candidates = new HashSet<>();
		while(res.next()) {
			int idCandidate = res.getInt("id");
			String name = res.getString("name");
			boolean isParty = res.getBoolean("isparty");
			Votable v;
			if (isParty) {
				// può essere un metodo a parte per aumentare il riuso 
				Set<Candidato> candidatesInParty = new HashSet<>();
				String thirdQuery = "SELECT * FROM voto.partytable as pt JOIN voto.candidates as c ON pt.idpartymember = c.id WHERE pt.idparty = ?";
				s = con.prepareStatement(thirdQuery);
				s.setInt(1, idCandidate);
				ResultSet resCandidates = s.executeQuery();
				s.close();
				while(resCandidates.next()) {
					int idCandidateParty = resCandidates.getInt("id");
					String nameCandidateParty = resCandidates.getString("name");
					candidatesInParty.add(new Candidato(idCandidateParty, nameCandidateParty));
				}
				resCandidates.close();
				// fine metodo in comune
				v = new Gruppo(idCandidate, name, candidatesInParty);
			} else {
				v = new Candidato(idCandidate, name);
			}
			candidates.add(v);
		}
		s.close();
		res.close();
		Map<Votable, Integer> votes = new HashMap<>();
		for (Votable v : candidates) {
			String fourthQuery = "SELECT * FROM voto.vote as v WHERE v.idsession = ? AND v.idcandidates = ?";
			s = con.prepareStatement(fourthQuery);
			s.setInt(1, idSessione);
			s.setInt(2, v.getId());
			res = s.executeQuery();
			while (res.next()) {
				int voti = res.getInt("preferenza");
				votes.put(v, voti);
			}
			res.close();
			s.close();
		}
		sf.setVotes(votes);
		con.close();
		return sf.getVotingSession();
	}

}

