package Model;
import java.util.*;

/**
 * 
 */
public class Elettore extends Utente {

    /**
     * 
     */
    private final Date dataCompleanno;

    /**
     * 
     */
    private String comune;

    /**
     * 
     */
    private String nazione;

    /**
     * 
     */
    private char sesso;


    /**
     * Default constructor
     */
    public Elettore(int id, String nome, String cognome, Date dataCompleanno, String nazione, char sesso) {
    	super(id, nome, cognome);
    	this.dataCompleanno = dataCompleanno;
    	this.setNazione(nazione);
    	this.setSesso(sesso);
    }

    /**
     * @return
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Elettore ID: " + this.get_id() + " chiamato: " + this.get_name() + " di sesso: " + sesso + " nato il: " + dataCompleanno + " a: " + comune + " in: " + nazione);
        return s.toString();
    }

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public Date getDataCompleanno() {
		return dataCompleanno;
	}

	public String getNazione() {
		return nazione;
	}

	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	public char getSesso() {
		return sesso;
	}

	public void setSesso(char sesso) {
		this.sesso = sesso;
	}

}