package Model;

/**
 * 
 */
public class Utente {

    /**
     * 
     */
    private final int id;

    /**
     * 
     */
    private String nome;

    /**
     * 
     */
    private String cognome;


    /**
     * Default constructor
     */
    public Utente(int id, String nome, String cognome) {
    	this.id = id;
    	this.cognome = cognome;
    	this.nome = nome;
    }
    
    /**
     * @return
     */
    public int get_id() {
        return id;
    }

    /**
     * @return
     */
    public String get_name() {
        return nome;
    }

    /**
     * @return
     */
    public String get_surname() {
        return cognome;
    }

    /**
     * @return
     */
    public String toString() {
        return "Utente con id: " + id + "; Nome: " + nome + "; Cognome: " + cognome + "\n";
    }

}