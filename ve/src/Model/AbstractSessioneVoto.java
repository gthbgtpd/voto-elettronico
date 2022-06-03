package Model;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AbstractSessioneVoto {

    public void addCandidates(Set<Votable> v);

    public void addCandidates(Votable v);

    public void beginVotingSession() throws IllegalBeginningVotingSession;

    public void endVotingSession();

	public Date getBeginningDate();

	public void setBeginningDate(Date beginningDate);

	public Date getEndingDate();

	public void setEndingDate(Date endingDate);
	
    public int getId();

    public boolean isOpen();

    public boolean isScrutinyPhase();

	public String getName();

    public Votable getWinner();

    public Map<Votable, Integer> vota(List<Votable> preferences);
    
}
