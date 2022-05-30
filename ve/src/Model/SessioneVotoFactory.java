package Model;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class SessioneVotoFactory implements AbstractSessioneVotoFactory {

    private int id;
    
    private String name;

    private Map<Votable, Integer> votes;

    private boolean isOpen;
    
    private boolean isScrutinyPhase;
    
    private int howManyHaveVoted;
    
    private String modalitaVincitaName;
    
    private String modalitaVotoName;
    
    private Date beginningDate;
    
    private Date endingDate;

    private Set<Votable> candidates;

    public SessioneVotoFactory() {}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setVotes(Map<Votable, Integer> votes) {
		this.votes = votes;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public void setScrutinyPhase(boolean isScrutinyPhase) {
		this.isScrutinyPhase = isScrutinyPhase;
	}

	public void setHowManyHaveVoted(int howManyHaveVoted) {
		this.howManyHaveVoted = howManyHaveVoted;
	}

	public void setDefinizioneVincitore(String modalitaVincitaName) {
		this.modalitaVincitaName = modalitaVincitaName;
	}

	public void setBeginningDate(Date beginningDate) {
		this.beginningDate = beginningDate;
	}

	public void setEndingDate(Date endingDate) {
		this.endingDate = endingDate;
	}

	public void setCandidates(Set<Votable> candidates) {
		this.candidates = candidates;
	}
	
	public void setModalitaVotoName(String modalitaVotoName) {
		this.modalitaVotoName = modalitaVotoName;
	}
    
	public SessioneVoto getVotingSession() {
		SessioneVoto s = new SessioneVoto(id, name, beginningDate, endingDate, votes, isOpen, isScrutinyPhase, candidates);
		s.setDefinizioneVincitore(modalitaVincitaName);
		s.setModalitaVoto(modalitaVotoName);
		s.setHowManyHaveVoted(howManyHaveVoted);
		return s;
	}

}
