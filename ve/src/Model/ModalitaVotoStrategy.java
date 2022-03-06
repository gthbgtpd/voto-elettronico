package Model;

import java.util.Map;

public interface ModalitaVotoStrategy {
	
	public Map<Votable, Integer> voto (Votable preferenza, Map<Votable, Integer> storicoPreferenze); // dovrebbero esporre tutta la stessa intestazione del metodo
	
}
