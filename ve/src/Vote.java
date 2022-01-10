
public class Vote {
	private String candidate;
	private String preferenza;
	
	public Vote(String c, String p) {
		candidate = c;
		preferenza = p;
	}
	
	public String getCandidate() {
		return candidate;
	}
	
	public String getPreferenza() {
		return preferenza;
	}
}
