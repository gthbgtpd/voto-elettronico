package Model;

import java.util.Objects;

/**
 * 
 */
public class Candidato implements Votable {


	/**
     * 
     */
    private String name;

    /**
     * 
     */
    private final int id;

    /**
     * Default constructor
     */
    public Candidato(int id, String name) {
    	this.id = id;
    	this.name = name;
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

    @Override
	public String toString() {
		return "Candidato [name=" + name + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Candidato other = (Candidato) obj;
		return id == other.id && Objects.equals(name, other.name);
	}
    
}
