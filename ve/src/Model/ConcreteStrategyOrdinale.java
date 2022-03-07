package Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConcreteStrategyOrdinale implements ModalitaVotoStrategy  {

	private final int NUM_MAX_PREF = 100; // numero massimo di preferenze esprimibili in un voto ordinale
	
	public ConcreteStrategyOrdinale() {}
	
	public Map<Votable, Integer> voto (List<Votable> preferenze, Map<Votable, Integer> storicoPreferenze) {
		int punteggio = NUM_MAX_PREF;
		Map<Votable, Integer> preferenzeModificate = new HashMap<>();
		for (Votable candidato : preferenze) {
			int punteggioPrecedente = 0;
			if (storicoPreferenze.containsKey(candidato)) punteggioPrecedente = storicoPreferenze.get(candidato);
			preferenzeModificate.put(candidato, punteggio + punteggioPrecedente);
			punteggio--;
		}
		return preferenzeModificate;
	}
	
}
