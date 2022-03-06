package Model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * 
 */
public class Gruppo implements Votable, Iterable<Candidato> {
	

	/**
     * 
     */
    private final int id;

    /**
     * 
     */
    private String name;

    private Set<Candidato> candidates;

    /**
     * Default constructor
     */
    public Gruppo(int id, String name) {
    	this.id = id;
    	this.name = name;
    	this.candidates = new HashSet<Candidato>();
    }
    
    /**
     * Constructor used to build a pre-existing group/party
     */
    public Gruppo(int id, String name, Set<Candidato> candidates) {
    	this.id = id;
    	this.name = name;
    	this.candidates = candidates;
    }



    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * 
     */
    public void addCandidate(Candidato c) {
    	candidates.add(c);
    }
    
    /**
     * 
     */
    public void addAllCandidate(Set<Candidato> c) {
    	candidates.addAll(c);
    }

	@Override
	public Iterator<Candidato> iterator() {
		return candidates.iterator();
	}
	
	@Override
	public String toString() {
		return "Candidato [name=" + name + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(candidates, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Gruppo other = (Gruppo) obj;
		return Objects.equals(candidates, other.candidates) && id == other.id && Objects.equals(name, other.name);
	}

}
