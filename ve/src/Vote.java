
public class Vote {
	private String session;
	private String candidate;
	private String preferenza;
	
	public Vote(String s, String c, String p) {
		session = s;
		candidate = c;
		preferenza = p;
	}
	
	public String getSession() {
		return session;
	}
	
	public String getCandidate() {
		return candidate;
	}
	
	public String getPreferenza() {
		return preferenza;
	}
}
