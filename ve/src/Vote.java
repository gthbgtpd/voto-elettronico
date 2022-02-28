
public class Vote {
	private String idSession;
	private String idCandidates;
	private String preferenza;
	
	public Vote(String s, String c, String p) {
		idSession = s;
		idCandidates = c;
		preferenza = p;
	}
	
	public String getIdSession() {
		return idSession;
	}
	
	public String getIdCandidates() {
		return idCandidates;
	}
	
	public String getPreferenza() {
		return preferenza;
	}
}
