package Model;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConcreteStrategyCategoricoConPreferenza implements ModalitaVotoStrategy {

	public ConcreteStrategyCategoricoConPreferenza() {}
	
	public Map<Votable, Integer> voto(List<Votable> candidati, Map<Votable, Integer> candidatoConVoto) {
		Map<Votable, Integer> preferenzeModificate = new HashMap<>();
		for (Votable candidato:candidati) {
			int punteggioPrecedente = 0;
			if (candidatoConVoto.containsKey(candidato)) punteggioPrecedente = candidatoConVoto.get(candidato);
			preferenzeModificate.put(candidato, punteggioPrecedente + 1);
		}
		return preferenzeModificate;
	}
}
