package Model;

import java.util.List;
import java.util.Map;

public class ConcreteStrategyOrdinale {

	public ConcreteStrategyOrdinale() {}
	
	public Map<Votable, Integer> voto (List<Votable> preferenze, Map<Votable, Integer> storicoPreferenze) {
		int punteggio = preferenze.size();
		for (Votable candidato: preferenze) {
			int punteggioPrecedente = 0;
			if (storicoPreferenze.containsKey(candidato)) punteggioPrecedente = storicoPreferenze.get(candidato);
			storicoPreferenze.put(candidato, punteggio + punteggioPrecedente);
			punteggio--;
		}
		return storicoPreferenze; // tendenzialmente non sarebbe nemmeno necessario restituirlo perché la modifica avviene inplace
	}
	
}
