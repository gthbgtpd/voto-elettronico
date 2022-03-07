package Model;

import java.util.List;
import java.util.Map;

public interface ModalitaVotoStrategy {
	
	public Map<Votable, Integer> voto (List<Votable> candidati, Map<Votable, Integer> storicoPreferenze); // dovrebbero esporre tutta la stessa intestazione del metodo
	
}
