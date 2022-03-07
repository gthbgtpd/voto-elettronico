package Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConcreteStrategyCategorico implements ModalitaVotoStrategy {

	public ConcreteStrategyCategorico() {}
	
	public Map<Votable, Integer> voto (List<Votable> preferenze, Map<Votable, Integer> storicoPreferenze) {
		Votable preferenza = preferenze.get(0);
		int punteggioPrecedente = 0;
		Map<Votable, Integer> preferenzeModificate = new HashMap<>();
		if (storicoPreferenze.containsKey(preferenza)) punteggioPrecedente = storicoPreferenze.get(preferenza);
		preferenzeModificate.put(preferenza, punteggioPrecedente + 1);
		return preferenzeModificate; // tendenzialmente non sarebbe nemmeno necessario restituirlo perché la modifica avviene inplace
	}
}
