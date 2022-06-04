
// connessione Zou: final String url = "jdbc:postgresql://localhost/votoelettronico?user=root&password=rootroot";
// connessione Liuc: final String url = "jdbc:mysql://localhost/voto?user=root&password=rootroot";

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Model.Candidato;
import Model.Gruppo;
import Model.SessioneVoto;
import Model.SessioneVotoFactory;
import Model.Votable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class AdminViewDao {
	private static Connection getConnection() throws SQLException {
		final String url = "jdbc:mysql://localhost/voto?user=root&password=rootroot";
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
		PreparedStatement query1 = conn.prepareStatement("insert into voto.session(id, name, modeVote, modeWin, howmanyhavevoted) values(?, ?, ?, ?, 0)");
		query1.setInt(1, getLastId("voto.session")+1);
        query1.setString(2, name);
        query1.setString(3, modeVote);
        query1.setString(4, modeWin);
        @SuppressWarnings("unused")
		boolean res = query1.execute();
	}
	
	public static void modifySession(String oldName, String name, String modeVote, String modeWin, Date dataApertura, Date dataChiusura) throws SQLException {
		Connection conn = getConnection();
		
		String sql = "update voto.session set name=?, modeVote=?, modeWin=?";
		if (dataApertura==null) {
			sql = sql+", dataapertura=?";
			if (dataChiusura==null) {
				sql = sql+", datachiusura=?";
				sql = sql+" where id=?";
				PreparedStatement query = conn.prepareStatement(sql);
				if (name.length()>0) {
					query.setString(1, name);
				} else {
					query.setString(1, oldName);
				}
		        query.setString(2, modeVote);
		        query.setString(3, modeWin);
		        query.setDate(4, dataApertura);
		        query.setDate(5, dataChiusura);
		        query.setInt(6, getId("voto.session", oldName));
		        query.executeUpdate();
			} else {
				sql = sql+" where id=?";
				PreparedStatement query = conn.prepareStatement(sql);
				if (name.length()>0) {
					query.setString(1, name);
				} else {
					query.setString(1, oldName);
				}
		        query.setString(2, modeVote);
		        query.setString(3, modeWin);
		        query.setDate(4, dataApertura);
		        query.setInt(5, getId("voto.session", oldName));
		        query.executeUpdate();
			}
		} else {
			sql = sql+" where id=?";
			PreparedStatement query = conn.prepareStatement(sql);
			if (name.length()>0) {
				query.setString(1, name);
			} else {
				query.setString(1, oldName);
			}
	        query.setString(2, modeVote);
	        query.setString(3, modeWin);
	        query.setInt(4, getId("voto.session", oldName));
	        query.executeUpdate();
		}
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
		query1.setInt(1, getLastId("voto.candidates")+1);
		query1.setInt(2, getId("voto.session", session));
        query1.setString(3, candidate);
        query1.setBoolean(4, isGroup);
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
			int howManyHaveVoted = res.getInt("howmanyhavevoted");
			sf.setId(idSessione);
			sf.setName(nameSession);
			sf.setDefinizioneVincitore(modeWin);
			sf.setBeginningDate(beginningDate);
			sf.setEndingDate(endingDate);
			sf.setScrutinyPhase(scrutinyPhase);
			sf.setOpen(isOpen);
			sf.setModalitaVotoName(modeVote);
			sf.setHowManyHaveVoted(howManyHaveVoted);
		}
		s.close();
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
				while(resCandidates.next()) {
					int idCandidateParty = resCandidates.getInt("id");
					String nameCandidateParty = resCandidates.getString("name");
					candidatesInParty.add(new Candidato(idCandidateParty, nameCandidateParty));
				}
				s.close();
				// fine metodo in comune
				v = new Gruppo(idCandidate, name, candidatesInParty);
			} else {
				v = new Candidato(idCandidate, name);
			}
			candidates.add(v);
		}
		s.close();
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
		//System.out.println(sf.getVotingSession());
		return sf.getVotingSession();
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

 	public static void addCandidateParty(String partito, String candidato) throws SQLException {
 		Connection conn = getConnection();
 		int idCandidato = getId("voto.candidates", candidato);
 		int idPartito = getId("voto.candidates", partito);
 		PreparedStatement query = conn.prepareStatement("insert into voto.partytable(idparty, idpartymember) values(?, ?)");
 		query.setInt(1, idPartito);
 		query.setInt(2, idCandidato);
 		query.execute();
 	}

 	public static void getCandidates(MenuButton scegliCandidato) throws SQLException {
 		scegliCandidato.getItems().clear();
 		List<String> l = new ArrayList<>();
 		Connection conn = getConnection();
 		String sql = "select name from voto.candidates as c where c.isparty = 0" ;
 		PreparedStatement query = conn.prepareStatement(sql);
 		ResultSet r = query.executeQuery();
 		while(r.next()) {
 			l.add(r.getString("name"));
 		}

 		for (String i:l) {
     		MenuItem session = new MenuItem(i);
     		scegliCandidato.getItems().add(session);
     	}
 	}

 	public static void getParties(MenuButton scegliPartito) throws SQLException {
 		scegliPartito.getItems().clear();
 		List<String> l = new ArrayList<>();
 		Connection conn = getConnection();
 		String sql = "select name from voto.candidates as c where c.isparty = 1" ;
 		PreparedStatement query = conn.prepareStatement(sql);
 		ResultSet r = query.executeQuery();
 		while(r.next()) {
 			l.add(r.getString("name"));
 		}

 		for (String i:l) {
     		MenuItem session = new MenuItem(i);
     		scegliPartito.getItems().add(session);
     	}
 	}
}
