package Model;

import java.util.*;

/**
 * 
 */
public class Gestore extends Utente {

    /**
     * Default constructor
     */
    public Gestore(int id, String nome, String cognome) {
    	super(id, nome, cognome);
    }

    // nessuno dei metodi sottostanti servirà, questa classe probabilmente perirà lol
    
    /**
     * @param id 
     * @param v 
     * @return
     */
    public SessioneVoto createNewSession(int id, Set<Votable> v) {
        // TODO implement here
        return null;
    }

    /**
     * @param s 
     * @param v
     */
    public void addCandidates(SessioneVoto s, Set<Votable> v) {
        // TODO implement here
    }

    /**
     * @param s
     */
    public void beginVotingSession(SessioneVoto s) {
        // TODO implement here
    }

    /**
     * @param s
     */
    public void endVotingSession(SessioneVoto s) {
        // TODO implement here
    }

    /**
     * @param s 
     * @return
     */
    public Map<Votable, Integer> viewSessionResult(SessioneVoto s) {
        // TODO implement here
        return null;
    }

}