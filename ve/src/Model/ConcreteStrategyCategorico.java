package Model;

import java.util.Map;

public class ConcreteStrategyCategorico {

	public ConcreteStrategyCategorico() {}
	
	public Map<Votable, Integer> voto (Votable preferenza, Map<Votable, Integer> storicoPreferenze) {
		int punteggioPrecedente = 0;
		if (storicoPreferenze.containsKey(preferenza)) punteggioPrecedente = storicoPreferenze.get(preferenza);
		storicoPreferenze.put(preferenza, punteggioPrecedente + 1);
		return storicoPreferenze; // tendenzialmente non sarebbe nemmeno necessario restituirlo perché la modifica avviene inplace
	}
}
