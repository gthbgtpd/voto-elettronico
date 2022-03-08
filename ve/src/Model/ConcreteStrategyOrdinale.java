package Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConcreteStrategyOrdinale implements ModalitaVotoStrategy  {
	
	public ConcreteStrategyOrdinale() {}
	
	public Map<Votable, Integer> voto (List<Votable> preferenze, Map<Votable, Integer> storicoPreferenze) {
		int punteggio = storicoPreferenze.keySet().size();
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
