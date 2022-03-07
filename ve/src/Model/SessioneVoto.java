package Model;
import java.util.*;

/**
 * 
 */
public class SessioneVoto {

    /**
     * 
     */
    private final int id;
    
    private final String name;

    /**
     * 
     */
    private Map<Votable, Integer> votes;

    /**
     * 
     */
    private boolean isOpen;
    
    /**
     * 
     */
    private boolean isScrutinyPhase;
    
    private int howManyHaveVoted;
    
    private ModalitaVincitaContext definizioneVincitore;
    
    private ModalitaVotoContext modalitaVoto;
    
    private Date beginningDate;
    
    private Date endingDate;

    /**
     * 
     */
    public Set<Votable> candidates;


	
    /**
     *  Used to construct a new object
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
     * @param v
     */
    public void addCandidates(Set<Votable> v) {
        candidates.addAll(v);
    }

    /**
     * @param v
     */
    public void addCandidates(Votable v) {
    	candidates.add(v);
    }

    /**
     * @throws IllegalBeginningVotingSession if the scrutiny phase has already started
     */
    public void beginVotingSession() throws IllegalBeginningVotingSession {
    	if (isScrutinyPhase) throw new IllegalBeginningVotingSession();
    	isOpen = true;
    }

    /**
     * 
     */
    public void endVotingSession() {
    	isOpen = false;
    	isScrutinyPhase = true;
    }

    /**
     * @param v
     */
    public void addVote(Votable v) {
    	Integer howManyVotes = this.votes.get(v);
    	howManyVotes++;
    	votes.put(v, howManyVotes);
    }
    
    /**
     * @param v
     */
    public void addMultipleVotes(Votable v, int points) {
    	Integer howManyVotes = this.votes.get(v);
    	howManyVotes += points;
    	votes.put(v, howManyVotes);
    }

	public int getId() {
		return id;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public Date getBeginningDate() {
		return beginningDate;
	}

	public void setBeginningDate(Date beginningDate) {
		this.beginningDate = beginningDate;
	}

	public Date getEndingDate() {
		return endingDate;
	}

	public void setEndingDate(Date endingDate) {
		this.endingDate = endingDate;
	}
	
	public void setHowManyHaveVoted(int howManyHaveVoted) {
		this.howManyHaveVoted = howManyHaveVoted;
	}

	public String getName() {
		return name;
	}

	public String getTipoDefinizioneVincitore() {
		return definizioneVincitore.getNomeModalitaVincita();
	}

	public void setDefinizioneVincitore(String nomeModalitaVincita) {
		this.definizioneVincitore.setStrategy(nomeModalitaVincita);
	}
	
	public String getTipoModalitaVoto() {
		return modalitaVoto.getNomeModalitaVoto();
	}

	public void setModalitaVoto(String nomeModalitaVoto) {
		this.modalitaVoto.setStrategy(nomeModalitaVoto);
	}
	
	public Votable getWinner() {
		return definizioneVincitore.definizioneVincitore(votes, howManyHaveVoted);
	}
	
	public Map<Votable, Integer> vota(List<Votable> preferences) {
		return modalitaVoto.vota(preferences, votes);
	}

	public int getHowManyHaveVoted() {
		return howManyHaveVoted;
	}

}
