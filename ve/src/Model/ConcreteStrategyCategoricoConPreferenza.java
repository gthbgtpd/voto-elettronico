import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ConcreteStrategyCategoricoConPreferenza {

	public ConcreteStrategyCategoricoConPreferenza() {}
	
	public void voto(List<Votable> candidati, Map<Votable, Integer> candidatoConVoto) throws SQLException {
		for (Votable candidato:candidati) {
			AdminClientDao.putVote(candidato, candidatoConVoto.get(candidato)+1);
		}
		return;
	}
}
