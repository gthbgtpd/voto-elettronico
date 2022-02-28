package Model;

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

}