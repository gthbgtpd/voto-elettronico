import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
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
	
	public static void getOpenSessions(MenuButton scegliSessione) throws SQLException, ParseException {
		openSessions();
		closeSessions();
		scegliSessione.getItems().clear();
		List<String> l = new ArrayList<>();
		Connection conn = getConnection();
		String sql = "select s.name from voto.session as s where s.isopen=true" ;
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
	
		public static Votable getCandidate(String nameCandidate) throws SQLException {
		Connection conn = getConnection();
		String sql = "select * from voto.candidates as c where c.name=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, nameCandidate);
		ResultSet r = stmt.executeQuery();
		Votable v = null;
		while(r.next()) {
			int id = r.getInt("id");
			boolean isParty = r.getBoolean("isparty");
			if (isParty) {
				v = new Gruppo(id, nameCandidate);
			} else {
				v = new Candidato(id, nameCandidate);
			}
		}
		conn.close();
		return v;
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
		return sf.getVotingSession();
	}

	
	public static void openSessions() throws SQLException, ParseException {
		Connection conn = getConnection();
		String sql = "select s.dataapertura from voto.session as s where s.dataapertura is not null" ;
		PreparedStatement query = conn.prepareStatement(sql);
		ResultSet r = query.executeQuery();
		
		List<Date> dates = new ArrayList<>();
		while(r.next()) {
			dates.add(r.getDate(1));
		}
		for (Date i:dates) {
			String s1 = i.toString();
			LocalDate d = LocalDate.now();
			String s2 = d.toString();
			if (s1.equals(s2)) {
				PreparedStatement query1 = conn.prepareStatement("update voto.session set isopen=?, fasescrutinio=? where isopen=false and dataapertura=?");
		        query1.setBoolean(1, true);
		        query1.setBoolean(2, false);
		        query1.setDate(3, i);
		        query1.executeUpdate();
			}
		}
	}
	
	public static void closeSessions() throws SQLException {
		Connection conn = getConnection();
		String sql = "select s.datachiusura from voto.session as s where s.dataapertura is not null" ;
		PreparedStatement query = conn.prepareStatement(sql);
		ResultSet r = query.executeQuery();
		
		List<Date> dates = new ArrayList<>();
		while(r.next()) {
			dates.add(r.getDate(1));
		}
		for (Date i:dates) {
			String s1 = i.toString();
			LocalDate d = LocalDate.now();
			String s2 = d.toString();
			if (s1.equals(s2)) {
				PreparedStatement query1 = conn.prepareStatement("update voto.session set isopen=?, fasescrutinio=? where isopen=true and datachiusura=?");
		        query1.setBoolean(1, false);
		        query1.setBoolean(2, true);
		        query1.setDate(3, i);
		        query1.executeUpdate();
			}
			
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
