package Model;

public class IllegalBeginningVotingSession extends Exception {

	private static final long serialVersionUID = 2752615146213840904L;
	
	public IllegalBeginningVotingSession() {
		super();
	}
	
	public IllegalBeginningVotingSession(String s) {
		super(s);
	}
}
