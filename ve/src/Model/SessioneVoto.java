package Model;
import java.util.*;


/**
 * It's a class that represents a voting session
 */
public class SessioneVoto {

    /**
	 * A unique identifier for the object.
	 */ 
	private final int id;
    
	/**
     * The name of the voting session
     */
	private final String name;

    /**
     * A map that stores the votes for each candidate.
     */
	private Map<Votable, Integer> votes;

    /**
     * A variable that stores whether the voting session is open or not. 
	 * If it's open than the user will be able to vote
     */
	private boolean isOpen;
    
    /**
     * A variable that stores whether the voting session is in the scrutiny phase or not.
	 * If the scrutiny phase has begun, the users won't be able to vote.
     */
	private boolean isScrutinyPhase;
    
    /** 
	 * A variable that stores the number of people who have voted.
	 */
	private int howManyHaveVoted;
    
    /**
	 * A variable that stores the strategy for defining the winner of the voting session.
	 */ 
	private ModalitaVincitaContext definizioneVincitore;
    
    /**
	 * A variable that stores the strategy for defining the voting method of the voting session.
	 */ 
	private ModalitaVotoContext modalitaVoto;
 
    /**
	 *  A variable that stores the beginning date of the voting session.
	 */ 
    private Date beginningDate;
    
    /**
	 *  A variable that stores the ending date of the voting session.
	 */ 
	private Date endingDate;

    /**
	 * A set of candidates that can be voted for.
	 */
	public Set<Votable> candidates;


	
    /**
     * Used to construct a new object
     */
    public SessioneVoto(int id, String name, Date beginningDate, Date endingDate) {
    	this.id = id;
    	this.name = name;
    	this.votes = new HashMap<>();
    	this.isOpen = false;
    	this.isScrutinyPhase = false;
    	this.setBeginningDate(beginningDate);
    	this.setEndingDate(endingDate);
    	this.candidates = new HashSet<>();
    	this.modalitaVoto = new ModalitaVotoContext();
    	this.definizioneVincitore = new ModalitaVincitaContext();
    }
    
    /**
     * Used to re-construct pre-existing objects, mainly when extracted from the database
     */
    public SessioneVoto(int id, String name, Date beginningDate, Date endingDate, Map<Votable, Integer> votes, boolean isOpen, boolean isScrutinyPhase, Set<Votable> candidates) {
    	this.id = id;
    	this.name = name;
    	this.votes = votes;
    	this.isOpen = isOpen;
    	this.isScrutinyPhase = isScrutinyPhase;
    	this.setBeginningDate(beginningDate);
    	this.setEndingDate(endingDate);
    	this.candidates = candidates;
    	this.modalitaVoto = new ModalitaVotoContext();
    	this.definizioneVincitore = new ModalitaVincitaContext();
    }

    /**
	 * Adds all the Votable Candidates of the set v to the candidates in this session.
	 * 
	 * @param v The set of candidates to add to the list of candidates.
	 */
	public void addCandidates(Set<Votable> v) {
        candidates.addAll(v);
    }

    /**
	 * Adds a Votable Candidate to the candidates list
	 * 
	 * @param v The Votable Candidate to add to the list of candidates.
	 */
	public void addCandidates(Votable v) {
    	candidates.add(v);
    }

    /**
	 * Starts the voting session
	 * 
     * @throws IllegalBeginningVotingSession if the scrutiny phase has already started
     */
	public void beginVotingSession() throws IllegalBeginningVotingSession {
    	if (isScrutinyPhase) throw new IllegalBeginningVotingSession();
    	isOpen = true;
    }

    /**
     * Ends the voting session and starts the scrutiny phase
     */
    public void endVotingSession() {
    	isOpen = false;
    	isScrutinyPhase = true;
    }

	/**
	 * This function returns the id of the object.
	 * 
	 * @return The id of the object.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns true if the VotingSession is open, false otherwise.
	 * 
	 * @return The value of the isOpen variable.
	 */
	public boolean isOpen() {
		return isOpen;
	}

	/**
	 * Returns the beginning date of the VotingSession
	 * 
	 * @return The date when the Session will begin.
	 */
	public Date getBeginningDate() {
		return beginningDate;
	}

	/**
	 * Sets the beginning date of the VotingSession.
	 * 
	 * @param beginningDate The date to start the Session from.
	 */
	public void setBeginningDate(Date beginningDate) {
		this.beginningDate = beginningDate;
	}

	/**
	 * Returns the ending date of the VotingSession.
	 * 
	 * @return The date when the Session will end.
	 */
	public Date getEndingDate() {
		return endingDate;
	}

	/**
	 * Sets the ending date of the VotingSession
	 * 
	 * @param endingDate The date when the promotion ends.
	 */
	public void setEndingDate(Date endingDate) {
		this.endingDate = endingDate;
	}
	
	/**
	 * Returns true if the VotingSession is in the scrutiny phase
	 * 
	 * @return The value of the isScrutinyPhase variable.
	 */
	public boolean isScrutinyPhase() {
		return isScrutinyPhase;
	}

	/**
	 * Returns the number of people who have voted
	 * 
	 * @return The number of people who have voted.
	 */
	public int getHowManyHaveVoted() {
		return howManyHaveVoted;
	}

	/**
	 * Sets the value of the variable howManyHaveVoted to the value of the parameter
	 * howManyHaveVoted
	 * 
	 * @param howManyHaveVoted This is the number of people who have voted on the VotingSession.
	 */
	public void setHowManyHaveVoted(int howManyHaveVoted) {
		if (howManyHaveVoted < 0) throw new IllegalArgumentException("Il numero di persone che hanno votato non può assumere valori negativi");
		this.howManyHaveVoted = howManyHaveVoted;
	}

	/**
	 * Returns the name of the VotingSession.
	 * 
	 * @return The name of the VotingSession.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the name of the winning mode
	 * 
	 * @return The name of the winning mode.
	 */
	public String getTipoDefinizioneVincitore() {
		return definizioneVincitore.getNomeModalitaVincita();
	}

	/**
	 * Sets the winning mode strategy to the one specified by the parameter
	 * 
	 * @param nomeModalitaVincita the name of the strategy to use.
	 */
	public void setDefinizioneVincitore(String nomeModalitaVincita) {
		this.definizioneVincitore.setStrategy(nomeModalitaVincita);
	}
	
	/**
	 * Returns the name of the voting method
	 * 
	 * @return The name of the voting method.
	 */
	public String getTipoModalitaVoto() {
		return modalitaVoto.getNomeModalitaVoto();
	}

	/**
	 * Sets the voting method strategy to the one specified by the parameter
	 * 
	 * @param nomeModalitaVincita the name of the strategy to use.
	 */
	public void setModalitaVoto(String nomeModalitaVoto) {
		this.modalitaVoto.setStrategy(nomeModalitaVoto);
	}
	
	 /**
 	 * Returns the winner of the voting session
 	 * 
 	 * @return The winner of the voting session.
 	 */
	public Votable getWinner() {
		return definizioneVincitore.definizioneVincitore(votes, howManyHaveVoted);
	}
	
	/**
	 * Takes a list of preferences and returns a map of votable objects and their
	 * votes containing the updated preferences after the vote has occurred.
	 * 
	 * @param preferences the list of votables that the voter wants to vote for
	 * @return A map of Votable and Integer containing the updated prefrences after the vote.
	 */
	public Map<Votable, Integer> vota(List<Votable> preferences) {
		howManyHaveVoted++;
		return modalitaVoto.vota(preferences, votes);
	}

	
	@Override
	public String toString() {
		return "SessioneVoto [id=" + id + ", name=" + name + ", votes=" + votes + ", isOpen=" + isOpen
				+ ", isScrutinyPhase=" + isScrutinyPhase + ", howManyHaveVoted=" + howManyHaveVoted
				+ ", definizioneVincitore=" + definizioneVincitore + ", modalitaVoto=" + modalitaVoto
				+ ", beginningDate=" + beginningDate + ", endingDate=" + endingDate + ", candidates=" + candidates
				+ "]";
	}
}
