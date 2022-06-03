package Model;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public interface AbstractSessioneVotoFactory {

    public void setId(int id);

	public void setName(String name);

	public void setVotes(Map<Votable, Integer> votes);

    public void setCandidates(Set<Votable> candidates);

	public void setOpen(boolean isOpen);

	public void setScrutinyPhase(boolean isScrutinyPhase);

	public void setBeginningDate(Date beginningDate);

	public void setEndingDate(Date endingDate);
    
	public AbstractSessioneVoto getVotingSession();
}
